package com.example.android.learningtimekeeper.learningquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.learningtimekeeper.database.LearningTimeDatabaseDao
import kotlinx.coroutines.launch

class LearningQualityViewModel(
        private val learningTimeKey: Long = 0L,
        dataSource: LearningTimeDatabaseDao) : ViewModel() {

    val database = dataSource

    private val _navigateToLearningTracker = MutableLiveData<Boolean?>()

    val navigateToLearningTracker: LiveData<Boolean?>
        get() = _navigateToLearningTracker

    fun doneNavigating() {
        _navigateToLearningTracker.value = null
    }

    /**
     *设置学习任务质量并更新数据库
     * 然后导航返回 [LearningTrackerFragment]页面
     */
    fun onSetLearningQuality(quality: Int) {
        viewModelScope.launch {
            val currentTime = database.get(learningTimeKey)
            currentTime.learningQuality = quality
            database.update(currentTime)

            _navigateToLearningTracker.value = true
        }
    }
}
