package com.example.android.learningtimekeeper.learningtracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.learningtimekeeper.database.LearningTimeDatabaseDao

class LearningTrackerViewModelFactory(
        private val dataSource: LearningTimeDatabaseDao,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LearningTrackerViewModel::class.java)) {
            return LearningTrackerViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
