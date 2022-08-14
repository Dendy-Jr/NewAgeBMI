package com.olehvynnytskyi.android.bmi.core.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.widget.NumberPicker
import com.olehvynnytskyi.android.bmi.R
import com.olehvynnytskyi.android.bmi.core.extensions.getColorFromAttr

class BmiNumberPicker(context: Context, attributeSet: AttributeSet) :
    NumberPicker(context, attributeSet) {

    private val paint = Paint().apply {
        color = context.getColorFromAttr(R.attr.colorMain)
    }

    init {
        context.setTheme(R.style.BMINumberPickerTheme)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            selectionDividerHeight = 0
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val pointTop = (height / 2).toFloat() + 25
            canvas.drawRect(0f, pointTop, width.toFloat(), pointTop + 10, paint)
        }
    }
}