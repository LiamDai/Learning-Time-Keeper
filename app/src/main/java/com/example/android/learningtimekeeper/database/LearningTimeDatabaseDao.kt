package com.example.android.learningtimekeeper.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * DAO Annotations
 * @Insert，@Delete和@Update直接使用，
 * @Query 要结合SQL语句
 */
@Dao
interface LearningTimeDatabaseDao {

    @Insert
    suspend fun insert(time: LearningTime)

    @Update
    suspend fun update(time: LearningTime)

    /**
     *选择并返回key所指定的开始时间的所有行
     */
    @Query("SELECT * from learning_quality_table WHERE timeId = :key")
    suspend fun get(key: Long): LearningTime

    /**
     *删除table中的所有值，而不删除table
     */
    @Query("DELETE FROM learning_quality_table")
    suspend fun clear()

    /**
     * 选择并返回table中的所有行
     *以开始时间分类，以降序排列
     */
    @Query("SELECT * FROM learning_quality_table ORDER BY timeId DESC")
    fun getAllTimes(): LiveData<List<LearningTime>>

    /**
     * 选择并返回制最新的time
     */
    @Query("SELECT * FROM learning_quality_table ORDER BY timeId DESC LIMIT 1")
    suspend fun getCurrentTime(): LearningTime?

    /**
     * 选择并返回指定timeID的time
     */
    @Query("SELECT * from learning_quality_table WHERE timeId = :key")
    fun getTimeWithId(key: Long): LiveData<LearningTime>
}
