package com.olehvynnytskyi.android.bmi.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.olehvynnytskyi.android.bmi.core.base.BaseViewModel
import com.olehvynnytskyi.android.bmi.data.BmiRepository
import com.olehvynnytskyi.android.bmi.domain.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BmiDetailsViewModel @Inject constructor(
    private val repository: BmiRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val args = BmiDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val _result = MutableStateFlow(0.0)
    val result = _result.asStateFlow()

    private val _category = MutableStateFlow("")
    val category = _category.asStateFlow()

    private val _ponderalIndex = MutableStateFlow(0.0)
    val ponderalIndex = _ponderalIndex.asStateFlow()

    init {
        getBmiResult()
        getPonderalIndex()
        getMbiCategory()
    }

    private fun getBmiResult() {
        viewModelScope.launch {
            repository.calculateBmi(
                Person(
                    weight = args.weight.toDouble(),
                    height = args.height.toDouble(),
                    gender = args.gender
                )
            ).collect { result ->
                _result.value = result
            }
        }
    }

    private fun getPonderalIndex() {
        viewModelScope.launch {
            repository.calculatePonderalIndex(
                Person(
                    weight = args.weight.toDouble(),
                    height = args.height.toDouble()
                )
            ).collect { ponderalIndex ->
                _ponderalIndex.value = ponderalIndex
            }
        }
    }

    private fun getMbiCategory() {
        viewModelScope.launch {
            repository.getMbiCategory().collect { category ->
                _category.value = category
            }
        }
    }
}