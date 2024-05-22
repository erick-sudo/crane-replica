package com.compose.samples.replicas.crane.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    Column(
        modifier
            .fillMaxSize()
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_crane_drawer),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(50.dp))

        LinearProgressIndicator(
            modifier = Modifier
                .height(5.dp)
                .width(150.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun LandingScreenPreview() {
    LandingScreen {

    }
}