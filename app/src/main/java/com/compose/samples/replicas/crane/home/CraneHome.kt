package com.compose.samples.replicas.crane.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.compose.samples.replicas.crane.R
import com.compose.samples.replicas.crane.base.CraneTabBar
import com.compose.samples.replicas.crane.base.CraneTabs
import com.compose.samples.replicas.crane.base.ExploreSection
import com.compose.samples.replicas.crane.data.ExploreModel
import kotlinx.coroutines.launch

typealias OnExploreItemClicked = (ExploreModel) -> Unit

enum class CraneScreen {
    Fly, Sleep, Eat
}

private const val TAB_SWITCH_ANIM_DURATION = 300

@Composable
fun CraneHome(
    onExploreItemClicked: OnExploreItemClicked,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier.statusBarsPadding(),
    ) { contentPadding ->
        val scope = rememberCoroutineScope()

        CraneHomeContent(
            modifier = modifier.padding(contentPadding),
            viewModel = viewModel,
            onExploreItemClicked = onExploreItemClicked
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CraneHomeContent(
    onExploreItemClicked: OnExploreItemClicked,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val suggestedDestinations by viewModel.suggestedDestinations.observeAsState()

    val onPeopleChange: (Int) -> Unit = { viewModel.updatePeople(it) }
    val craneScreenValues = CraneScreen.entries.toTypedArray()

    val pagerState = rememberPagerState(initialPage = CraneScreen.Fly.ordinal) {
        craneScreenValues.size
    }

    val coroutineScope = rememberCoroutineScope()

    Column {
        HomeTabBar(
            modifier = Modifier,
            tabSelected = craneScreenValues[pagerState.currentPage],
            onTabSelected = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        it.ordinal,
                        animationSpec = tween(
                            TAB_SWITCH_ANIM_DURATION
                        )
                    )
                }
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            HorizontalPager(state = pagerState) { page ->
                when(craneScreenValues[page]) {
                    CraneScreen.Fly -> {
                        ExploreSection(
                            title = stringResource(id = R.string.explore_flights_by_destination),
                            exploreList = suggestedDestinations ?: emptyList(),
                            onItemClicked = onExploreItemClicked
                        )
                    }
                    CraneScreen.Sleep -> {
                        ExploreSection(
                            title = stringResource(id = R.string.explore_properties_by_destination),
                            exploreList = viewModel.hotels,
                            onItemClicked = onExploreItemClicked
                        )
                    }
                    CraneScreen.Eat -> {
                        ExploreSection(
                            title = stringResource(id = R.string.explore_restaurants_by_destination),
                            exploreList = viewModel.restaurants,
                            onItemClicked = onExploreItemClicked
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeTabBar(
    tabSelected: CraneScreen,
    onTabSelected: (CraneScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    CraneTabBar(
        modifier = modifier,
        onMenuClicked = { /*TODO*/ }
    ) { tabBarModifier ->
        CraneTabs(
            modifier = tabBarModifier,
            titles = CraneScreen.entries.map { it.name },
            tabSelected = tabSelected,
            onTabSelected = onTabSelected
        )
    }
}

@Composable
fun SearchContent(
    tabSelected: CraneScreen,
    viewModel: MainViewModel,
    onPeopleChanged: (Int) -> Unit,
    onDateSelectionClicked: () -> Unit,
    onExploreItemClicked: OnExploreItemClicked
) {

//    AnimatedContent(
//        targetState = tabSelected,
//        label = "SearchContent",
//        transitionSpec = {
//            fadeIn(
//                animationSpec = tween(TAB_SWITCH_ANIM_DURATION, easing = EaseIn )
//            ).togetherWith(
//                fadeOut(
//                    animationSpec = tween(TAB_SWITCH_ANIM_DURATION, easing = EaseOut)
//                )
//            ).using(
//                SizeTransform(
//                    sizeAnimationSpec = { _, _ ->
//                        tween(TAB_SWITCH_ANIM_DURATION, easing = EaseInOut)
//                    }
//                )
//            )
//        }
//    ) { targetState ->
//        when (targetState) {
//            CraneScreen.Fly -> FlySearchContent(
//                datesSelected = selectedDates,
//                searchUpdates = FlySearchContentUpdates(
//                    onPeopleChanged = onPeopleChanged,
//                    onToDestinationChanged = { viewModel.toDestinationChanged(it) },
//                    onDateSelectionClicked = onDateSelectionClicked,
//                    onExploreItemClicked = onExploreItemClicked
//                )
//            )
//
//            CraneScreen.Sleep -> SleepSearchContent(
//                datesSelected = selectedDates,
//                sleepUpdates = SleepSearchContentUpdates(
//                    onPeopleChanged = onPeopleChanged,
//                    onDateSelectionClicked = onDateSelectionClicked,
//                    onExploreItemClicked = onExploreItemClicked
//                )
//            )
//
//            CraneScreen.Eat -> EatSearchContent(
//                datesSelected = selectedDates,
//                eatUpdates = EatSearchContentUpdates(
//                    onPeopleChanged = onPeopleChanged,
//                    onDateSelectionClicked = onDateSelectionClicked,
//                    onExploreItemClicked = onExploreItemClicked
//                )
//            )
//        }
//    }
}

data class FlySearchContentUpdates(
    val onPeopleChanged: (Int) -> Unit,
    val onToDestinationChanged: (String) -> Unit,
    val onDateSelectionClicked: () -> Unit,
    val onExploreItemClicked: OnExploreItemClicked
)

data class SleepSearchContentUpdates(
    val onPeopleChanged: (Int) -> Unit,
    val onDateSelectionClicked: () -> Unit,
    val onExploreItemClicked: OnExploreItemClicked
)

data class EatSearchContentUpdates(
    val onPeopleChanged: (Int) -> Unit,
    val onDateSelectionClicked: () -> Unit,
    val onExploreItemClicked: OnExploreItemClicked
)