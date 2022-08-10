package com.olehvynnytskyi.android.bmi.presentation.calculate

import com.olehvynnytskyi.android.bmi.core.base.BaseViewModel
import com.olehvynnytskyi.android.bmi.domain.model.Person.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class BmiCalculateViewModel @Inject constructor() : BaseViewModel() {

    private val name = MutableStateFlow("")
    private val weight = MutableStateFlow(30)
    private val height = MutableStateFlow(100)
    private val gender = MutableStateFlow(Gender.MALE)

    fun toCalculateClicked() {
        navigateTo(
            BmiCalculateFragmentDirections.toBmiDetailsScreen(
                name = name.value,
                weight = weight.value,
                height = height.value,
                gender = gender.value
            )
        )
    }

    fun getName(name: String) {
        this.name.value = name
    }

    fun getWeight(weight: Int) {
        this.weight.value = weight
    }

    fun getHeight(height: Int) {
        this.height.value = height
    }

    fun getGender(value: Int) {
        val gender = if (value == 0) Gender.MALE else Gender.FEMALE
        this.gender.value = gender
    }
}