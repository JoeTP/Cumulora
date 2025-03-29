package com.example.cumulora

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cumulora.data.local.sharedpref.SharedPreferenceHelper
import com.example.cumulora.features.splash.SplashScreenUI
import com.example.cumulora.features.splash.SplashViewModel
import com.example.cumulora.navigation.NavSetup
import com.example.cumulora.receiver.AlarmCancelReceiver
import com.example.cumulora.receiver.AlarmReceiver
import com.example.cumulora.ui.theme.CumuloraTheme
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.LANG
import com.google.android.libraries.places.api.Places
import java.util.Locale


class MainActivity : ComponentActivity() {
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenceHelper.initSharedPref(this)
        Places.initializeWithNewPlacesApiEnabled(this, BuildConfig.googleApiKey)
        CURRENT_LANG = SharedPreferenceHelper.getInstance().getData(LANG, "en")
        applyLanguage(CURRENT_LANG)

        val intentFilter = IntentFilter("com.example.cumulora.receiver.AlarmReceiver")
        registerReceiver(AlarmReceiver(), intentFilter)

        val intentFilter2 = IntentFilter("com.example.cumulora.receiver.AlarmCancelReceiver")
        registerReceiver(AlarmCancelReceiver(), intentFilter2)

        setContent { CumuloraTheme { AppContent() } }
    }

    fun applyLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
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


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun MainLayout(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        NavSetup(navController, snackbarHostState)
    }
}