package com.olehvynnytskyi.android.bmi.presentation.calculate

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.olehvynnytskyi.android.bmi.R
import com.olehvynnytskyi.android.bmi.core.base.BaseFragment
import com.olehvynnytskyi.android.bmi.databinding.FragmentBmiCalculateBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BmiCalculateFragment : BaseFragment<BmiCalculateViewModel>(R.layout.fragment_bmi_calculate) {

    private val binding: FragmentBmiCalculateBinding by viewBinding()
    override val viewModel: BmiCalculateViewModel by viewModels()

    private var mInterstitialAd: InterstitialAd? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle()
        onBind()
        loadInterstitialAd()
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

        btnCalculate.setOnClickListener {
            showInterstitialAd()
        }
    }

    private fun setToolbarTitle() {
        requireActivity().findViewById<TextView>(R.id.tvTitleToolbar).apply {
            text = getString(R.string.add_bmi_details)
        }
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            requireContext(),
            getString(R.string.interstitial_ad_id),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let {
                        Timber.d(it)
                    }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Timber.d("Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
            mInterstitialAd = null
        } else {
            viewModel.toCalculateClicked()
            Timber.d("The interstitial ad wasn't ready yet.")
        }
    }
}