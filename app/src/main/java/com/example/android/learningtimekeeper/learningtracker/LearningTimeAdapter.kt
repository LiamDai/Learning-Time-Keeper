package com.example.android.learningtimekeeper.learningtracker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.learningtimekeeper.R
import com.example.android.learningtimekeeper.database.LearningTime
import com.example.android.learningtimekeeper.databinding.ListItemLearningTimeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

/**
 * 适配器模式把数据转化为RecycleView可以显示的内容，
 * RecycleView不直接与View交互而是使用ViewHolder来这么做。
 */

class LearningTimeAdapter(val clickListener: LearningTimeListener):
        ListAdapter<DataItem, RecyclerView.ViewHolder>(LearningTimeDiffCallback()) {

    /**
     * 协程的特点是：1.异步 2.非阻塞的 3.顺序化的(通过suspending function)
     *
     *协程由三个基本部分组成：
     *
     * 1. Job是可以被取消的任何东西，通常用于父子层级关系中，一旦父级任务取消，所有子级任务也会被取消
     *
     * 2. Dispatcher 把协程分配到不同的线程
     * Main是主线程，也称为UI线程，用于速度非常快的UI或者非阻塞代码；
     * IO是一个线程池，用于运行访问网络和磁盘的操作；
     * Default也是一个线程池，用于CPU操作
     *
     * 3. Scope结合job和dispatcher来决定运行运行环境。我们也称它为结构化并发，它被设计来解决内存泄漏问题。
     * 可以使用CoroutineScope自定义一个Scope，通常为 Dispathcers + Job 的方式。
     * 同时架构组件提供了已经配置好的Scope，使用时需要添加相应的依赖。
     * 它们分别是 ViewModelScope，LifecycleScope，liveData
     */

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<LearningTime>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.LearningTimeItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    /**
     * onBindViewHolder()通过重用ViewHolder的方式来回收View，
     * 例如，如果在需要绑定第三个位置时滚出屏幕，
     * 那么将回收不再使用的ViewHolder，并把其传递给onBindViewHolder()
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val timeItem = getItem(position) as DataItem.LearningTimeItem
                holder.bind(timeItem.learningTime, clickListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.LearningTimeItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }


    class ViewHolder private constructor(val binding: ListItemLearningTimeBinding) : RecyclerView.ViewHolder(binding.root){

        /**
         * 因为Adapter中可能拥有多个ViewHolder，
         * 所以把bind方法重构为扩展函数
         */
        fun bind(item: LearningTime, clickListener: LearningTimeListener) {
            binding.learning = item
            binding.clickListener = clickListener
            //使用executePendingBindings使其更快调整View的Size
            binding.executePendingBindings()
        }

        /**
         * 因为我们希望从ViewHolder类而不是ViewHolder实例上调用from方法，
         * 所以重构为companion object。
         * 现在适配器只需从父级调用ViewHolder即可创建新的ViewHolder。
         * 这样可以帮助我们分离关注点SOP，
         * 适配器负责调整数据传递给RecycleView API，ViewHolder负责管理视图。
         */
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemLearningTimeBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

/**
 * DiffUtil会调用两个方法，
 * 第一次方法判断item是否与旧位置的item是同一个，
 * 第二个方法判断内容是否相等。
 * data class会提供equal方法，所以这里用 == 可以检查全部内容是否相等
 */
class LearningTimeDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}


class LearningTimeListener(val clickListener: (learningId: Long) -> Unit) {
    fun onClick(night: LearningTime) = clickListener(night.timeId)
}


sealed class DataItem {
    data class LearningTimeItem(val learningTime: LearningTime): DataItem() {
        override val id = learningTime.timeId
    }

    object Header: DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}

