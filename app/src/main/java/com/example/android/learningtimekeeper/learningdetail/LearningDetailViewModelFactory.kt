package com.example.android.learningtimekeeper.learningdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.learningtimekeeper.database.LearningTimeDatabaseDao

/**
 * 使用ViewModelFactory确保当ViewModel创建后总有一个数据库。
 * 所以让Factory提供ViewModel的依赖，而不是让ViewModel的创建依赖于database。
 * 当需要在ViewModel刚初始化就使用数据时，使用ViewModelFactory。
 *
 * 为ViewModel提供LearningTimeDatabaseDao和learningTimeKey。
 */
class LearningDetailViewModelFactory(
        private val learningTimeKey: Long,
        private val dataSource: LearningTimeDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LearningDetailViewModel::class.java)) {
            return LearningDetailViewModel(learningTimeKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}