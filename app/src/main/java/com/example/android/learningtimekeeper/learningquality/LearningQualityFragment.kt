package com.example.android.learningtimekeeper.learningquality

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
import com.example.android.learningtimekeeper.databinding.FragmentLearningQualityBinding

class LearningQualityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentLearningQualityBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_learning_quality, container, false)

        //requireNotNull会在值为空时抛出合理的异常
        val application = requireNotNull(this.activity).application
        val arguments = LearningQualityFragmentArgs.fromBundle(arguments)

        val dataSource = LearningTimeDatabase.getInstance(application).learningTimeDatabaseDao
        val viewModelFactory = LearningQualityViewModelFactory(arguments.learningTimeKey, dataSource)

        val learningQualityViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(LearningQualityViewModel::class.java)

        binding.learningQualityViewModel = learningQualityViewModel

        learningQualityViewModel.navigateToLearningTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                        LearningQualityFragmentDirections.actionLearningQualityFragmentToLearningTrackerFragment())
                learningQualityViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}
