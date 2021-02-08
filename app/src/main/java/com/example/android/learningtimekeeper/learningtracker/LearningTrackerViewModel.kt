package com.example.android.learningtimekeeper.learningtracker

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.learningtimekeeper.database.LearningTimeDatabaseDao
import com.example.android.learningtimekeeper.database.LearningTime
import com.example.android.learningtimekeeper.formatTimes
import kotlinx.coroutines.launch

/**
 * ViewModel中没有Room的模版
 */
//    fun someWorkNeedsToBeDone {
//        viewModelScope.launch {
//            suspendFunction()
//        }
//    }
//
//    suspend fun suspendFunction() {
//        withContext(Dispatchers.IO) {
//            longrunningWork()
//        }
//    }
//
/**
 * ViewModel中使用Room的模版
 * Room使用在后台Dispatcher.IO线程中执行数据库操作。
 * 你不必显式地指定任何Dispatchers,因为Room已经做好了这个。
 */
//    fun someWorkNeedsToBeDone {
//        viewModelScope.launch {
//            suspendDAOFunction()
//        }
//    }
//
//    suspend fun suspendDAOFunction() {
//        longrunningDatabaseWork()
//    }

class LearningTrackerViewModel(
        dataSource: LearningTimeDatabaseDao,
        application: Application) : ViewModel() {

    val database = dataSource

    private var currentTime = MutableLiveData<LearningTime?>()

    val times = database.getAllTimes()

    /**
     * 转换time数据为可展示的格式
     */
    val timesString = Transformations.map(times) { times ->
        formatTimes(times, application.resources)
    }

    val startButtonVisible = Transformations.map(currentTime) {
        null == it
    }

    val stopButtonVisible = Transformations.map(currentTime) {
        null != it
    }

    val clearButtonVisible = Transformations.map(times) {
        it?.isNotEmpty()
    }

    private var _showSnackbarEvent = MutableLiveData<Boolean?>()

    val showSnackBarEvent: LiveData<Boolean?>
        get() = _showSnackbarEvent

    private val _navigateToLearningQuality = MutableLiveData<LearningTime>()

    val navigateToLearningQuality: LiveData<LearningTime>
        get() = _navigateToLearningQuality

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
    }

    fun doneNavigating() {
        _navigateToLearningQuality.value = null
    }

    private val _navigateToLearningDetail = MutableLiveData<Long>()
    val navigateToLearningDetail
        get() = _navigateToLearningDetail

    fun onLearningTimeClicked(id: Long) {
        _navigateToLearningDetail.value = id
    }

    fun onLearningDetailNavigated() {
        _navigateToLearningDetail.value = null
    }

    init {
        initializeCurrentTime()
    }

    private fun initializeCurrentTime() {
        viewModelScope.launch {
            currentTime.value = getTimeFromDatabase()
        }
    }

    /**
     * 处理应用停止或忘记记录的情况，此时开始和结束时间将是相同的。
     *
     * 如果开始时间和结束时间不一样，我们不会有一个未完成记录。
     */
    private suspend fun getTimeFromDatabase(): LearningTime? {
        var time = database.getCurrentTime()
        if (time?.endTimeMilli != time?.startTimeMilli) {
            time = null
        }
        return time
    }

    private suspend fun insert(time: LearningTime) {
        database.insert(time)
    }

    private suspend fun update(time: LearningTime) {
        database.update(time)
    }

    private suspend fun clear() {
        database.clear()
    }

    fun onStart() {
        viewModelScope.launch {
            val newTime = LearningTime()

            insert(newTime)

            this@LearningTrackerViewModel.currentTime.value = getTimeFromDatabase()
        }
    }

    fun onStop() {
        viewModelScope.launch {
            // 在Kotlin中，return@label 语法用于指定返回于几个嵌套对象中的哪个函数
            // 在这个例子中，我们指定从launch()返回。
            val oldTime = currentTime.value ?: return@launch

            // Update the night in the database to add the end time.
            oldTime.endTimeMilli = System.currentTimeMillis()

            update(oldTime)

            _navigateToLearningQuality.value = oldTime
        }
    }

    fun onClear() {
        viewModelScope.launch {
            // 清除数据库的table。
            clear()

            // 清除currentTime
            currentTime.value = null

            // 展示一个snackbar信息来提示用户。
            _showSnackbarEvent.value = true
        }
    }
}