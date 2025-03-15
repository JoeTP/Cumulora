package com.example.cumulora.features.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cumulora.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreenUI(onSplashEnd: () -> Unit) {
    val lottie by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.splash))
    var isAnimationEnded by remember { mutableStateOf(false) }

    val progress by animateLottieCompositionAsState(
        composition = lottie,
        iterations = 1,
        restartOnPlay = false,
        speed = 1f,
        isPlaying = true,
    )

    LaunchedEffect(Unit) {
        delay(1500)
        onSplashEnd()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LottieAnimation(
            composition = lottie,
            progress = { progress },
            modifier = Modifier.size(250.dp)
        )
    }
}