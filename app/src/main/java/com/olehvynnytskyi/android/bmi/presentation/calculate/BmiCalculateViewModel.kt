package com.olehvynnytskyi.android.bmi.presentation.calculate

import com.olehvynnytskyi.android.bmi.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BmiCalculateViewModel @Inject constructor() : BaseViewModel() {

    fun toCalculateClicked() {
        navigateTo(BmiCalculateFragmentDirections.toBmiDetailsScreen())
    }
}