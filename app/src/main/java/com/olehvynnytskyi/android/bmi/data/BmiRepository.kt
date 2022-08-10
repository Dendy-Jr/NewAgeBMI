package com.olehvynnytskyi.android.bmi.data

import android.content.Context
import com.olehvynnytskyi.android.bmi.R
import com.olehvynnytskyi.android.bmi.domain.model.Person
import com.olehvynnytskyi.android.bmi.domain.model.Person.Gender
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BmiRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var result = 0.0

    fun calculateBmi(person: Person): Flow<Double> = flow {
        if (person.gender == Gender.MALE) {
            bmiCalMale(person.weight, person.height)
        } else {
            bmiCalFemale(person.weight, person.height)
        }
        emit(result)
        Timber.d(result.toString())
    }

    fun getMbiCategory(): Flow<String> = flow {
        val category = when {
            result < 16.0 -> context.getString(R.string.underweight_severe_thinness)
            result in 16.0..16.9 -> context.getString(R.string.Underweight_moderate_thinness)
            result in 17.0..18.4 -> context.getString(R.string.underweight_mild_thinness)
            result in 18.5..24.9 -> context.getString(R.string.normal_range)
            result in 25.0..29.9 -> context.getString(R.string.overweight_pre_obese)
            result in 30.0..34.9 -> context.getString(R.string.obese_class_1)
            result in 35.0..39.9 -> context.getString(R.string.obese_class_2)
            result >= 40.0 -> context.getString(R.string.obese_class_3)
            else -> ""
        }
        emit(category)
    }

    fun calculatePonderalIndex(person: Person): Flow<Double> = flow {
        val heightPi = person.height / 100
        val ponderalIndex = (person.weight / (heightPi * heightPi * heightPi))
        emit(ponderalIndex)
    }

    private fun bmiCalMale(weight: Double, height: Double) {
        result = ((weight / (height * height)) * 10000)
    }

    private fun bmiCalFemale(weight: Double, height: Double) {
        result = ((weight / (height * height)) * 10000)
    }
}