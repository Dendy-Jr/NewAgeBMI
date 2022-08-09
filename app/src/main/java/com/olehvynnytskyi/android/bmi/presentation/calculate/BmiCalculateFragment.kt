package com.olehvynnytskyi.android.bmi.presentation.calculate

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olehvynnytskyi.android.bmi.R
import com.olehvynnytskyi.android.bmi.databinding.FragmentBmiCalculateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BmiCalculateFragment : Fragment(R.layout.fragment_bmi_calculate) {

    private val binding: FragmentBmiCalculateBinding by viewBinding()
    private val viewModel: BmiCalculateViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle()
        onBind()
    }

    private fun onBind() = with(binding) {
        npWeight.minValue = 30
        npWeight.maxValue = 150

        npHeight.minValue = 100
        npHeight.maxValue = 250

        val gender = arrayOf("Male", "Female")
        npGender.minValue = 0
        npGender.maxValue = gender.size - 1
        npGender.displayedValues = gender
    }

    //TODO Create BaseFragment
    private fun setToolbarTitle() {
        requireActivity().findViewById<TextView>(R.id.tvTitleToolbar).apply {
            text = getString(R.string.add_bmi_details)
        }
    }
}