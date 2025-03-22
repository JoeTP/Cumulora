package com.example.cumulora

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cumulora.component.MyAppBar
import com.example.cumulora.features.splash.SplashScreenUI
import com.example.cumulora.features.splash.SplashViewModel
import com.example.cumulora.navigation.NavSetup
import com.example.cumulora.navigation.ScreenRoutes
import com.example.cumulora.ui.theme.CumuloraTheme
import com.example.cumulora.utils.SharedPrefManager
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initializeWithNewPlacesApiEnabled(this, BuildConfig.googleApiKey)
        SharedPrefManager.initialize(this)

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


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainLayout(navController: NavHostController) {
    Scaffold( snackbarHost = {}) { paddingValues ->
        NavSetup(navController)
    }
}
