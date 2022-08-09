package com.olehvynnytskyi.android.bmi.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olehvynnytskyi.android.bmi.R
import com.olehvynnytskyi.android.bmi.databinding.FragmentBmiDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BmiDetailsFragment : Fragment(R.layout.fragment_bmi_details) {

    private val binding: FragmentBmiDetailsBinding by viewBinding()
    private val viewModel: BmiDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}