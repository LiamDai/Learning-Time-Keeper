package com.example.android.learningtimekeeper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * 创建Room Database
 * 1. 创建继承自Room数据库的公共抽象类
 * 2. 声明class，entities和版本号
 * 3. 在companion object中定义用于返回DAO的抽象方法和属性
 * 4. 用database builder 创建单例模式的database
 */

@Database(entities = [LearningTime::class], version = 1, exportSchema = false)
abstract class LearningTimeDatabase : RoomDatabase() {

    /**
     * 连接数据库和DAO.
     */
    abstract val learningTimeDatabaseDao: LearningTimeDatabaseDao

    /**
     * 定义一个companion object，使我们可以在LearningTimeDatabase类上添加函数。
     *
     * 例如，客户端可以调用' LearningTimeDatabase.getInstance(context) '来实例化一个新的数据库。
     */
    companion object {

        /**
         * INSTANCE将保留通过getInstance返回的任何数据库的引用，
         * 这将帮助我们避免重复初始化数据库。
         *
         * volatile用于确保instance的值始终保持更新和所有执行的线程相同。
         * volatile变量的值永远不会被缓存，并且所有读写操作都在主内存中进行。
         * 这意味着它的改变对所有其它线程可见，确保不会发生两个线程各自更新缓存中相同实体。
         * 也就是我们常说的阻止指令重排。
         */
        @Volatile
        private var INSTANCE: LearningTimeDatabase? = null

        /**
         * getInstance()是获取数据库的辅助函数。
         *
         * 如果数据库已经被检索到，则返回前一个数据库。
         * 否则，创建一个新的数据库。
         *
         * 这个函数是线程安全的，调用者应该缓存多个数据库调用的结果，以避免更多开销。
         * This function is threadsafe, and callers should cache the result for multiple database
         * calls to avoid overhead.
         *
         * @param context 应用程序环境单例，用于访问文件系统。
         */
        fun getInstance(context: Context): LearningTimeDatabase {
            // 多个线程可能同时请求数据库
            // synchronized block确保一次只有一个线程能进入临界区，确保数据库只能初始化一次。
            synchronized(this) {

                //复制INSTANCE的当前值到一个局部变量instance，以便Kotlin可以智能转换。
                //智能转换只对局部变量有效。
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            LearningTimeDatabase::class.java,
                            "learning_time_history_database"
                    )
                            // 如果没有迁移对象，则消除并重新构建，而不是迁移。
                            // 更多关于Room中如何迁移的信息：
                            // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                            .fallbackToDestructiveMigration()
                            .build()
                    // 赋值 INSTANCE 到新建到数据库。
                    INSTANCE = instance
                }

                // 返回 instance; 智能转换为 non-null.
                return instance
            }
        }
    }
}
