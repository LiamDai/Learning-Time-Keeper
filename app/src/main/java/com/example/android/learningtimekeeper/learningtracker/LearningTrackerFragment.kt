package com.example.android.learningtimekeeper.learningtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.learningtimekeeper.R
import com.example.android.learningtimekeeper.database.LearningTimeDatabase
import com.example.android.learningtimekeeper.databinding.FragmentLearningTrackerBinding
import com.google.android.material.snackbar.Snackbar

class LearningTrackerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentLearningTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_learning_tracker, container, false)

        //requireNotNull会在值为空时抛出合理的异常
        val application = requireNotNull(this.activity).application

        val dataSource = LearningTimeDatabase.getInstance(application).learningTimeDatabaseDao
        val viewModelFactory = LearningTrackerViewModelFactory(dataSource, application)

        val learningTrackerViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(LearningTrackerViewModel::class.java)

        binding.learningTrackerViewModel = learningTrackerViewModel

        val adapter = LearningTimeAdapter(LearningTimeListener { timeId ->
            //Toast.makeText(context, "${timeId}", Toast.LENGTH_LONG).show()
            learningTrackerViewModel.onLearningTimeClicked(timeId)
        })
        binding.learningList.adapter = adapter


        learningTrackerViewModel.times.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        //指定当前活动为绑定的生命周期所有者。
        //这是必须的，这样binding才能观察到LiveData的更新。
        binding.setLifecycleOwner(this)

        learningTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.cleared_message),
                        Snackbar.LENGTH_SHORT
                ).show()
                learningTrackerViewModel.doneShowingSnackbar()
            }
        })

        learningTrackerViewModel.navigateToLearningQuality.observe(viewLifecycleOwner, Observer { time ->
            time?.let {
                this.findNavController().navigate(
                        LearningTrackerFragmentDirections
                                .actionLearningTrackerFragmentToLearningQualityFragment(time.timeId))
                learningTrackerViewModel.doneNavigating()
            }
        })

        learningTrackerViewModel.navigateToLearningDetail.observe(viewLifecycleOwner, Observer { time ->
            time?.let {

                this.findNavController().navigate(
                        LearningTrackerFragmentDirections
                                .actionLearningTrackerFragmentToLearningDetailFragment(time))
                learningTrackerViewModel.onLearningDetailNavigated()
            }
        })

        val manager = GridLayoutManager(activity, 3)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) =  when (position) {
                0 -> 3
                else -> 1
            }
        }

        binding.learningList.layoutManager = manager

        return binding.root
    }
}
