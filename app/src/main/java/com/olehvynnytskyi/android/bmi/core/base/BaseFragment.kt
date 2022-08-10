package com.olehvynnytskyi.android.bmi.core.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.olehvynnytskyi.android.bmi.app.navigation.NavigationCommand
import com.olehvynnytskyi.android.bmi.core.extensions.collectWithLifecycle

abstract class BaseFragment<VM : BaseViewModel>(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId), ViewModelOwner<VM> {

    abstract override val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigation()
    }

    private fun observeNavigation() {
        collectWithLifecycle(viewModel.navigation) { event ->
            event?.getContentIfNotHandled()?.let { navigationCommand ->
                handleNavigation(navigationCommand)
            }
        }
    }

    private fun handleNavigation(navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.NavigateTo -> findNavController().navigate(navCommand.navDirections)
            is NavigationCommand.NavigateBack -> findNavController().navigateUp()
        }
    }
}