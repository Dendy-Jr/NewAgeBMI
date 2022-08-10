package com.olehvynnytskyi.android.bmi.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.MobileAds
import com.olehvynnytskyi.android.bmi.R
import com.olehvynnytskyi.android.bmi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {}
        onBind()
    }

    private fun onBind() = with(binding) {
        lifecycleScope.launch {
            delay(2_000L)
            appBarLayout.isVisible = true
            ivLogo.isVisible = false
            navContainer.isVisible = true
            getNavController()
        }
    }

    private fun getNavController() {
        val navHost = supportFragmentManager.findFragmentById(R.id.navContainer) as NavHostFragment
        navController = navHost.navController
    }
}