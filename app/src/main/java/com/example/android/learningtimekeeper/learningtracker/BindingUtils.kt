package com.example.android.learningtimekeeper.learningtracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.learningtimekeeper.R
import com.example.android.learningtimekeeper.convertDurationToFormatted
import com.example.android.learningtimekeeper.convertNumericQualityToString
import com.example.android.learningtimekeeper.database.LearningTime

/**
 * BindAdapter和RecycleView Adapter一样是一个Adapter，负责更新View
 */
@BindingAdapter("learningDurationFormatted")
fun TextView.setLearningDurationFormatted(item: LearningTime?) {
    item?.let {
        text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, context.resources)
    }
}


@BindingAdapter("learningQualityString")
fun TextView.setLearningQualityString(item: LearningTime?) {
    item?.let {
        text = convertNumericQualityToString(item.learningQuality, context.resources)
    }
}


@BindingAdapter("learningImage")
fun ImageView.setLearningImage(item: LearningTime?) {
    item?.let {
        setImageResource(when (item.learningQuality) {
            0 -> R.drawable.ic_learning_0
            1 -> R.drawable.ic_learning_1
            2 -> R.drawable.ic_learning_2

            3 -> R.drawable.ic_learning_3

            4 -> R.drawable.ic_learning_4
            5 -> R.drawable.ic_learning_5
            else -> R.drawable.ic_learning_active
        })
    }
}