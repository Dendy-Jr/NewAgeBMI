package com.olehvynnytskyi.android.bmi.app.navigation

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class NavigateTo(val navDirections: NavDirections) : NavigationCommand()
    object NavigateBack : NavigationCommand()
}