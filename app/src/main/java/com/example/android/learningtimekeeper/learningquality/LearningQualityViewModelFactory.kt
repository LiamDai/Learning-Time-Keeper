package com.example.android.learningtimekeeper.learningquality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.learningtimekeeper.database.LearningTimeDatabaseDao

class LearningQualityViewModelFactory(
        private val learningTimeKey: Long,
        private val dataSource: LearningTimeDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LearningQualityViewModel::class.java)) {
            return LearningQualityViewModel(learningTimeKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
