package com.olehvynnytskyi.android.bmi.core.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.olehvynnytskyi.android.bmi.app.navigation.NavigationCommand
import com.olehvynnytskyi.android.bmi.core.utils.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface ViewModelOwner<VM : BaseViewModel> {
    val viewModel: VM
}

open class BaseViewModel : ViewModel() {

    private val _navigation = MutableStateFlow<Event<NavigationCommand>?>(null)
    val navigation = _navigation.asStateFlow()

    fun navigateTo(navDirections: NavDirections) {
        _navigation.value = Event(NavigationCommand.NavigateTo(navDirections))
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.NavigateBack)
    }
}