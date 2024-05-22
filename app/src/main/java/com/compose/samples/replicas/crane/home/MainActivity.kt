package com.compose.samples.replicas.crane.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compose.samples.replicas.crane.calendar.CalendarScreen
import com.compose.samples.replicas.crane.ui.theme.CraneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CraneTheme(
                darkTheme = false,
                dynamicColor = false
            ) {

                val navHostController = rememberNavController()

                NavHost(
                    navController = navHostController,
                    startDestination = Routes.Home.route
                ) {
                    composable(Routes.Home.route) {
                        MainScreen(
                            onExploreItemClicked = {
                                // launchDetailsActivity(context = this@MainActivity, item = it)
                            }
                        )
                    }

                    composable(Routes.Calendar.route) {
                        val parentEntry = remember(it) {
                            navHostController.getBackStackEntry(Routes.Home.route)
                        }

                        CalendarScreen()
                    }
                }
            }
        }
    }
}

sealed class Routes(val route: String) {
    data object Home: Routes("home")

    data object Calendar: Routes("calendar")
}

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel(),
    onExploreItemClicked: OnExploreItemClicked,
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {

        val transitionState = remember {
            MutableTransitionState(mainViewModel.shownSplash.value)
        }
        val transition = updateTransition(transitionState, label = "splashTransition")
        val splashAlpha by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 100) }, label = "splashAlpha"
        ) {
            if(it == SplashState.Shown) 1f else 0f
        }
        val contentAlpha by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 300) }, label = "contentAlpha"
        ) {
            if(it == SplashState.Shown) 0f else 1f
        }
        val contentTopPadding by transition.animateDp(
            transitionSpec = { spring(stiffness = Spring.StiffnessLow) }, label = "contentTopPadding"
        ) {
            if(it == SplashState.Shown) 100.dp else 0.dp
        }

        Box {
            LandingScreen(
                modifier = Modifier.alpha(splashAlpha),
                onTimeOut = {
                    transitionState.targetState = SplashState.Completed
                    mainViewModel.shownSplash.value = SplashState.Completed
                }
            )

            MainContent(
                modifier = Modifier.alpha(contentAlpha),
                topPadding = contentTopPadding,
                viewModel = mainViewModel,
                onExploreItemClicked = onExploreItemClicked
            )
        }
    }
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    topPadding: Dp = 0.dp,
    viewModel: MainViewModel,
    onExploreItemClicked: OnExploreItemClicked,
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.padding(top = topPadding))
        CraneHome(
            viewModel = viewModel,
            onExploreItemClicked = onExploreItemClicked
        )
    }
}

enum class SplashState { Shown, Completed }