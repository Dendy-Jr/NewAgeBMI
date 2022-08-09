package com.olehvynnytskyi.android.bmi.core.extensions

import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.CharacterStyle
import androidx.core.text.inSpans
import kotlin.math.roundToInt

fun fontSizeSpan(size: Float, dp: Boolean): CharacterStyle =
    AbsoluteSizeSpan(size.roundToInt(), dp)

inline fun SpannableStringBuilder.fontSize(
    size: Float,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(fontSizeSpan(size, dp = true), builderAction = builderAction)