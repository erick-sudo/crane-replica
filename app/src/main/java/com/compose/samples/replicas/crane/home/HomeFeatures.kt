package com.compose.samples.replicas.crane.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FlySearchContent(
    datesSelected: String,
    searchUpdates: FlySearchContentUpdates
) {
    val columns = 2

    CraneSearch(
        columns = columns,
    ) {
        item {  }
    }
}

@Composable
fun SleepSearchContent(
    datesSelected: String,
    sleepUpdates: SleepSearchContentUpdates
) {
    val columns = 2
}

@Composable
fun EatSearchContent(
    datesSelected: String,
    eatUpdates: EatSearchContentUpdates
) {
    val columns = 2
}

@Composable
private fun CraneSearch(
    columns: Int,
    content: LazyGridScope.() -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(start = 24.dp, top = 0.dp, end = 24.dp, bottom = 12.dp),
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = content
    )
}