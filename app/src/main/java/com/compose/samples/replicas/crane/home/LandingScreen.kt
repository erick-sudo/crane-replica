package com.compose.samples.replicas.crane.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay
import com.compose.samples.replicas.crane.R

private const val SplashWaitTime: Long = 2000

@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    onTimeOut: () -> Unit
 ) {

    val currentOnTimeout by rememberUpdatedState(onTimeOut)

    LaunchedEffect(Unit) {
        delay(SplashWaitTime)
        currentOnTimeout()
    }

    Image(
        painter = painterResource(id = R.drawable.ic_crane_drawer),
        contentDescription = null,
        modifier
            .fillMaxSize()
            .wrapContentSize()
    )
}