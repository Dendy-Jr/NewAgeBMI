package com.olehvynnytskyi.android.bmi.presentation.details

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olehvynnytskyi.android.bmi.R
import com.olehvynnytskyi.android.bmi.core.base.BaseFragment
import com.olehvynnytskyi.android.bmi.core.extensions.collectWithLifecycle
import com.olehvynnytskyi.android.bmi.core.extensions.fontSize
import com.olehvynnytskyi.android.bmi.databinding.FragmentBmiDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BmiDetailsFragment : BaseFragment<BmiDetailsViewModel>(R.layout.fragment_bmi_details) {

    private val binding: FragmentBmiDetailsBinding by viewBinding()
    override val viewModel: BmiDetailsViewModel by viewModels()

    private val args: BmiDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle()
        omBind()
    }

    private fun omBind() = with(binding) {
        collectWithLifecycle(viewModel.category) { category ->
            tvGreeting.text =
                getString(R.string.bmi_details_greeting, args.name.uppercase().trim(), category)
        }
        collectWithLifecycle(viewModel.result) { result ->
            setResult(result)
        }
        collectWithLifecycle(viewModel.ponderalIndex) { ponderalIndex ->
            tvPonderalIndex.text = getString(R.string.bmi_ponderal_index, String.format("%.2f", ponderalIndex))
        }
    }

    private fun setResult(result: Double) {
        binding.tvFractionalResult.text = buildSpannedString {
            val resultOfFraction = String.format("%.2f", result).split(".")
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