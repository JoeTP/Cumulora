package com.example.cumulora

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cumulora.features.onboard.OnBoardingScreenUI
import com.example.cumulora.features.splash.SplashScreenUI
import com.example.cumulora.features.splash.SplashViewModel
import com.example.cumulora.navigation.NavSetup
import com.example.cumulora.ui.theme.CumuloraTheme
import com.example.cumulora.utils.SharedPrefManager

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        SharedPrefManager.initialize(this)
        super.onCreate(savedInstanceState)
        setContent {
            CumuloraTheme {
                AppContent(viewModel())
            }
        }
    }
}

@Composable
fun AppContent(viewModel: SplashViewModel) {
    val isSplashVisible = viewModel.isSplashVisible

    if (isSplashVisible) {
        SplashScreenUI(onSplashEnd = viewModel::hideSplash)
    } else {
        MainLayout()
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainLayout() {
    Scaffold {
        NavSetup()
    }
}