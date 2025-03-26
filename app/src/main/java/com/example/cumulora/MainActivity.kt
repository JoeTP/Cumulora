package com.example.cumulora

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cumulora.data.local.sharedpref.SharedPreferenceHelper
import com.example.cumulora.features.splash.SplashScreenUI
import com.example.cumulora.features.splash.SplashViewModel
import com.example.cumulora.navigation.NavSetup
import com.example.cumulora.ui.theme.CumuloraTheme
import com.google.android.libraries.places.api.Places


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Places.initializeWithNewPlacesApiEnabled(this, BuildConfig.googleApiKey)
        SharedPreferenceHelper.initSharedPref(this)
        setContent {
            CumuloraTheme {
                    AppContent()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppContent(navController: NavHostController = rememberNavController()) {

    val viewModel: SplashViewModel = viewModel()

    val isSplashVisible = viewModel.isSplashVisible

    if (isSplashVisible) {
        SplashScreenUI(onSplashEnd = viewModel::hideSplash)
    } else {
        MainLayout(navController)
    }
}


//@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun MainLayout(navController: NavHostController) {
    // Create the snackbar host state at the root level
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        // Pass the snackbarHostState to NavSetup
        NavSetup(navController, snackbarHostState)
    }
}