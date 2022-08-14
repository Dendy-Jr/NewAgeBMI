package com.olehvynnytskyi.android.bmi.core.extensions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

fun Context.openApplication(packageName: String, pageUrl: String, webUrl: String? = null) {
    try {
        packageManager.getPackageInfo(packageName, 0)
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(pageUrl)))
    } catch (e: PackageManager.NameNotFoundException) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(webUrl ?: pageUrl)))
    }
}

@ColorInt
fun Context.getColorFromAttr(@AttrRes attrColor: Int): Int {
    val typedArray = theme.obtainStyledAttributes(intArrayOf(attrColor))
    val textColor = typedArray.getColor(0, 0)
    typedArray.recycle()
    return textColor
}