package com.olehvynnytskyi.android.bmi.presentation.details

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olehvynnytskyi.android.bmi.R
import com.olehvynnytskyi.android.bmi.core.base.BaseFragment
import com.olehvynnytskyi.android.bmi.core.extensions.fontSize
import com.olehvynnytskyi.android.bmi.databinding.FragmentBmiDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BmiDetailsFragment : BaseFragment<BmiDetailsViewModel>(R.layout.fragment_bmi_details) {

    private val binding: FragmentBmiDetailsBinding by viewBinding()
    override val viewModel: BmiDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle()
    }

    private fun setResult(result: Double) {
        binding.tvFractionalResult.text = buildSpannedString {
            val resultOfFraction = result.toString().split(".")
            val wholePart = resultOfFraction[0]
            val fractionalPart = resultOfFraction[1]

            fontSize(86f) { append(wholePart) }
            fontSize(34f) {
                append(".")
                append(fractionalPart)
            }
        }
    }

    private fun setToolbarTitle() {
        requireActivity().findViewById<TextView>(R.id.tvTitleToolbar).apply {
            text = getString(R.string.bmi_details)
        }
    }
}