package com.zekri.mediaplayercompose.ui.file_browser

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.zekri.mediaplayercompose.data.FileType
import com.zekri.mediaplayercompose.domain.AppContainer
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalUnitApi::class)
@Composable
fun TabLayout(
    appContainer: AppContainer,
    modifier: Modifier,
    navHostController: NavHostController
) {
    // on below line we are creating variable for pager state.
    val pagerState = rememberPagerState(0)
    // on below line we are creating a column for our widgets.
    Column(
        // for column we are specifying modifier on below line.
        modifier =modifier
    ) {
        // on the below line we are specifying the top app bar
        // and specifying background color for it.
        TopAppBar {
            // on below line we are specifying a column
            // for our text view to display a text
            // in our top app bar.
            Column(
                modifier = Modifier.fillMaxSize(),
                // on below line we are providing alignment for our
                // column to center of our top app bar.
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // on below line we are specifying a text and
                // specifying a text as "Tab Layout Example"
                Text(
                    text = "Selection",
                    style = TextStyle(color = Color.White),
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(
                        18F,
                        TextUnitType.Sp
                    ),
                    // on below line we are specifying a modifier
                    // to our text and adding passing from all sides.
                    modifier = Modifier.padding(all = Dp(5F)),
                    // on below line we are aligning
                    // our text to center.
                    textAlign = TextAlign.Center
                )
            }
        }
        // on below line we are calling tabs
        Tabs(pagerState = pagerState)
        // on below line we are calling tabs content
        // for displaying our page for each tab layout
        TabsContent(
            pagerState = pagerState,
            modifier = modifier,
            navHostController = navHostController,
            appContainer = appContainer
        )
    }
}
// on below line we are
// creating a function for tabs
@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    // in this function we are creating a list
    // in this list we are specifying data as
    // name of the tab and the icon for it.
    val list = listOf(
        "Music" to Icons.Default.Image,
        "Images" to Icons.Default.LibraryMusic
    )
    // on below line we are creating
    // a variable for the scope.
    val scope = rememberCoroutineScope()
    // on below line we are creating a
    // individual row for our tab layout.
    TabRow(
        // on below line we are specifying
        // the selected index.
        selectedTabIndex = pagerState.currentPage,
        // on below line we are
        // specifying background color.
      //  backgroundColor = Color.Cyan,
        // on below line we are specifying content color.
        // contentColor = Color.White,
        // on below line we are specifying
        // the indicator for the tab
        indicator = { tabPositions ->
            // on below line we are specifying the styling
            // for tab indicator by specifying height
            // and color for the tab indicator.
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                // color = Color.White
            )
        }
    ) {
        // on below line we are specifying icon
        // and text for the individual tab item
        list.forEachIndexed { index, _ ->
            // on below line we are creating a tab.
            Tab(
                // on below line we are specifying icon
                // for each tab item and we are calling
                // image from the list which we have created.
                icon = {
                    Icon(imageVector = list[index].second, contentDescription = null)
                },
                // on below line we are specifying the text for
                // the each tab item and we are calling data
                // from the list which we have created.
                text = {
                    Text(
                        list[index].first,
                        // on below line we are specifying the text color
                        // for the text in that tab
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },
                // on below line we are specifying
                // the tab which is selected.
                selected = pagerState.currentPage == index,
                // on below line we are specifying the
                // on click for the tab which is selected.
                onClick = {
                    // on below line we are specifying the scope.
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}
// on below line we are creating a tab content method
// in which we will be displaying the individual page of our tab .
@ExperimentalPagerApi
@Composable
fun TabsContent(
    pagerState: PagerState,
    modifier: Modifier,
    appContainer: AppContainer,
    navHostController: NavHostController
) {
    // on below line we are creating
    // horizontal pager for our tab layout.
    HorizontalPager(state = pagerState, count = 2) {
        // on below line we are specifying
        // the different pages.
            page ->
        when (page) {
            0 -> {
                FileBrowser(
                    appContainer = appContainer,
                    modifier = modifier,
                    navHostController = navHostController,
                    fileType = FileType.AUDIO
                )
            }
            1 -> {
                FileBrowser(
                    appContainer = appContainer,
                    modifier = modifier,
                    navHostController = navHostController,
                    fileType = FileType.IMAGE
                )
            }
        }
    }
}
