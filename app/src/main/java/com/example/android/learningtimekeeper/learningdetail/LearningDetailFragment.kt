package com.example.android.learningtimekeeper.learningdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.learningtimekeeper.R
import com.example.android.learningtimekeeper.database.LearningTimeDatabase
import com.example.android.learningtimekeeper.databinding.FragmentLearningDetailBinding

class LearningDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //获得绑定的引用并且填充视图。
        val binding: FragmentLearningDetailBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_learning_detail, container, false)

        //requireNotNull会在值为空时抛出合理的异常
        val application = requireNotNull(this.activity).application
        val arguments = LearningDetailFragmentArgs.fromBundle(arguments)

        //创建一个ViewModel工厂的实例。
        val dataSource = LearningTimeDatabase.getInstance(application).learningTimeDatabaseDao
        val viewModelFactory = LearningDetailViewModelFactory(arguments.learningTimeKey, dataSource)

        // 永远使用ViewModelProvider来创建ViewModel
        //ViewModelProvider会在没有ViewModel时创建一个ViewModel，如果有则返回现有的
        //ViewModelProvider会创建与给定范围（activity或fragment）相关联的ViewModel实例
        val learningDetailViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(LearningDetailViewModel::class.java)

        //绑定ViewModel
        binding.learningDetailViewModel = learningDetailViewModel

        binding.setLifecycleOwner(this)

        //当icon被点击时，监听器触发导航
        learningDetailViewModel.navigateToLearningTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                        LearningDetailFragmentDirections.actionLearningDetailFragmentToLearningTrackerFragment())
                //重置状态以确保我们只导航一次，即使设备有一个配置被更改。
                learningDetailViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}