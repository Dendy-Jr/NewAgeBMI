package com.olehvynnytskyi.android.bmi.core.extensions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

fun Context.openApplication(packageName: String, pageUrl: String, webUrl: String? = null) {
    try {
        packageManager.getPackageInfo(packageName, 0)
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(pageUrl)))
    } catch (e: PackageManager.NameNotFoundException) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(webUrl ?: pageUrl)))
    }
}