package com.olehvynnytskyi.android.bmi.presentation.details

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.text.buildSpannedString
import androidx.core.view.drawToBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.nativead.NativeAd
import com.olehvynnytskyi.android.bmi.R
import com.olehvynnytskyi.android.bmi.core.base.BaseFragment
import com.olehvynnytskyi.android.bmi.core.extensions.collectWithLifecycle
import com.olehvynnytskyi.android.bmi.core.extensions.fontSize
import com.olehvynnytskyi.android.bmi.core.extensions.openApplication
import com.olehvynnytskyi.android.bmi.databinding.FragmentBmiDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class BmiDetailsFragment : BaseFragment<BmiDetailsViewModel>(R.layout.fragment_bmi_details) {

    private val binding: FragmentBmiDetailsBinding by viewBinding()
    override val viewModel: BmiDetailsViewModel by viewModels()

    private val args: BmiDetailsFragmentArgs by navArgs()

    private val defaultFileName = "BMI" + System.currentTimeMillis() + ".jpeg"

    private val requestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) saveAndShareScreenShot()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle()
        omBind()

        MobileAds.initialize(requireContext())
        populateNativeAdView()
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
            tvPonderalIndex.text =
                getString(R.string.bmi_ponderal_index, String.format("%.2f", ponderalIndex))
        }

        btnShare.setOnClickListener {
            saveAndShareScreenShot()
        }

        btnRate.setOnClickListener {
            requireContext().openApplication(
                packageName = getString(R.string.google_play_package),
                pageUrl = getString(R.string.google_play_url)
            )
        }
    }

    private fun saveAndShareScreenShot() {
        if (isStoragePermissionGranted().not()) {
            requestLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return
        }

        binding.cardView.drawToBitmap().let {
            saveBitmapToCache(it)
        }

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/jpeg"
        intent.putExtra(
            Intent.EXTRA_STREAM,
            FileProvider.getUriForFile(
                requireContext(), getString(R.string.authorities_fileprovider),
                File(requireContext().cacheDir, defaultFileName)
            )
        )
        startActivity(Intent.createChooser(intent, null))
    }

    private fun isStoragePermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    private fun setResult(result: Double) {
        binding.tvFractionalResult.text = buildSpannedString {
            val resultOfFraction = String.format("%.2f", result).split(".")
            val wholePart = resultOfFraction[0]
            val fractionalPart = resultOfFraction[1]

            fontSize(85f) { append(wholePart) }
            fontSize(33f) {
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

    private fun populateNativeAdView() {
        val adLoader = AdLoader.Builder(requireContext(), getString(R.string.admob_ad_unit_id))
            .forNativeAd { nativeAd ->
                displayNativeAd(nativeAd)
            }
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun displayNativeAd(nativeAd: NativeAd) = with(binding) {
        nativeAdView.apply {
            iconView = adIcon
            callToActionView = adBtnAction
            setNativeAd(nativeAd)
        }
        adHeadline.text = nativeAd.headline
        adBody.text = nativeAd.body
        adIcon.setImageDrawable(nativeAd.icon?.drawable)
    }

    private fun saveBitmapToCache(bitmap: Bitmap) {
        val cacheFile = File(requireContext().cacheDir, defaultFileName)
        val outputStream = FileOutputStream(cacheFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
    }
}