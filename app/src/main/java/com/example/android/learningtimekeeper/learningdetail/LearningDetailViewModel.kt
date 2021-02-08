package com.example.android.learningtimekeeper.learningdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.learningtimekeeper.database.LearningTimeDatabaseDao
import com.example.android.learningtimekeeper.database.LearningTime
import kotlinx.coroutines.Job

/**
 * @param learningTimeKey 当前正在记录的学习时间的关键字。
 */
class LearningDetailViewModel(
        private val learningTimeKey: Long = 0L,
        dataSource: LearningTimeDatabaseDao) : ViewModel() {

    /**
     * 通过它的SleepDatabaseDao获得一个对SleepDatabase的引用。
     */
    val database = dataSource

    /** 为协程设置一个Job
     * viewModelJob 使我们可以取消由此ViewModel启动的所有协程。
     */
    private val viewModelJob = Job()

    private val time: LiveData<LearningTime>

    fun getTime() = time

    init {
        time=database.getTimeWithId(learningTimeKey)
    }

    /**
     * 设置变量来确认是否导航到 [LearningTrackerFragment].
     */
    private val _navigateToLearningTracker = MutableLiveData<Boolean?>()

    val navigateToLearningTracker: LiveData<Boolean?>
        get() = _navigateToLearningTracker

    /**
     * *在ViewModel被清除时取消所有协程，以清除任何未决的工作。
     * onCleared()在ViewModel被销毁时被调用。
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * 导航到 [LearningTrackerFragment] 后立即调用这个方法
     */
    fun doneNavigating() {
        _navigateToLearningTracker.value = null
    }

    fun onClose() {
        _navigateToLearningTracker.value = true
    }
}