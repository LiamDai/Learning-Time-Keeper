package com.example.android.learningtimekeeper.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用data class定义entity / table，用annotation来添加源数据以识别类型，之后在DAO中用接口来定义如何交互
 */
@Entity(tableName = "learning_quality_table")
data class LearningTime(
        @PrimaryKey(autoGenerate = true)
        var timeId: Long = 0L,

        @ColumnInfo(name = "start_time_milli")
        val startTimeMilli: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "end_time_milli")
        var endTimeMilli: Long = startTimeMilli,

        @ColumnInfo(name = "quality_rating")
        var learningQuality: Int = -1)
