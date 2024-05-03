package com.cpimca.scrapwala

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeFregment(navController: NavController, onItemSelected: (Int) -> Unit) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .padding(top = 66.dp, bottom = 75.dp)
            .fillMaxSize(),
    ) {
        val pagerState = rememberPagerState(initialPage = 0)

        val imageSlider = listOf(
            painterResource(id = R.drawable.img_banner1),
            painterResource(id = R.drawable.img_banner2),
            painterResource(id = R.drawable.img_banner3)
        )

        LaunchedEffect(Unit) {
            while (true) {
                yield()
                delay(2600)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % (pagerState.pageCount)
                )
            }
        }
        Column(
            modifier = Modifier,
        ) {
            HorizontalPager(
                count = imageSlider.size,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier
                    .height(154.dp)
                    .fillMaxWidth()
            ) { page ->

                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                val scaleValue = animateFloatAsState(
                    targetValue = 1f - pageOffset,
                    animationSpec = tween(durationMillis = 1000)
                )

                val alphaValue = animateFloatAsState(
                    targetValue = 1f - pageOffset,
                    animationSpec = tween(durationMillis = 600)
                )
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scaleValue.value
                            scaleY = scaleValue.value
                            alpha = alphaValue.value

                        }
                ) {
                    Image(
                        painter = imageSlider[page],
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = Color(0xFF0E5204D19),
                inactiveColor = Color(0xE5B2D6AC),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
            LazyColumn {
                item {
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .height(140.dp)
                            .fillMaxWidth()
                            .border(
                                0.5.dp,
                                color = Color(0xFFE5204D19),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .height(133.dp)
                                .fillMaxWidth()
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    navController.navigate(Route.ScrapService)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .height(0.5.dp)
                                    .background(Color(0xFFE5204D19))
                                    .fillMaxWidth()
                            )
                            Text(
                                text = "Scrap Service",
                                style = TextStyle(
                                    color = Color(0xFF0E5204D19)
                                )
                            )
                            Spacer(
                                modifier = Modifier
                                    .height(0.5.dp)
                                    .background(Color(0xFFE5204D19))
                                    .fillMaxWidth()
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(start = 15.dp, top = 10.dp, bottom = 10.dp),
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "Sell your Scrap in bulk",
                                        style = TextStyle(
                                            color = Color.Black
                                        )
                                    )
                                    Text(
                                        modifier = Modifier.padding(top = 10.dp),
                                        text = "See the price and then book our service",
                                        style = TextStyle(
                                            color = Color.Black
                                        )
                                    )
                                }
                                Image(
                                    painter = painterResource(id = R.drawable.intro2),
                                    contentDescription = "android image",
                                    modifier = Modifier
                                        .size(110.dp)
                                        .align(Alignment.CenterVertically)
                                        .padding(end = 15.dp)
                                )
                            }

                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .height(140.dp)
                            .border(
                                0.5.dp,
                                color = Color(0xFFE5204D19),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .height(133.dp)
                                .fillMaxWidth()
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    navController.navigate(Route.CategoryService)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .height(0.5.dp)
                                    .background(Color(0xFFE5204D19))
                                    .fillMaxWidth()
                            )
                            Text(
                                text = "Category Service",
                                style = TextStyle(
                                    color = Color(0xFF0E5204D19)
                                )
                            )
                            Spacer(
                                modifier = Modifier
                                    .height(0.5.dp)
                                    .background(Color(0xFFE5204D19))
                                    .fillMaxWidth()
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(start = 15.dp, top = 10.dp, bottom = 10.dp),
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "Sell your Scrap category wise",
                                        style = TextStyle(
                                            color = Color.Black
                                        )
                                    )
                                    Text(
                                        modifier = Modifier.padding(top = 10.dp),
                                        text = "See the price and then book our service",
                                        style = TextStyle(
                                            color = Color.Black
                                        )
                                    )
                                }
                                Image(
                                    painter = painterResource(id = R.drawable.intro2),
                                    contentDescription = "android image",
                                    modifier = Modifier
                                        .size(110.dp)
                                        .align(Alignment.CenterVertically)
                                        .padding(end = 15.dp)
                                )
                            }
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .height(140.dp)
                            .border(
                                0.5.dp,
                                color = Color(0xFFE5204D19),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .height(133.dp)
                                .fillMaxWidth()
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    navController.navigate(Route.MonthlyPickUpService)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .height(0.5.dp)
                                    .background(Color(0xFFE5204D19))
                                    .fillMaxWidth()
                            )
                            Text(
                                text = "Monthly Pick-Up Service",
                                style = TextStyle(
                                    color = Color(0xFF0E5204D19)
                                )
                            )
                            Spacer(
                                modifier = Modifier
                                    .height(0.5.dp)
                                    .background(Color(0xFFE5204D19))
                                    .fillMaxWidth()
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(start = 15.dp, top = 10.dp, bottom = 10.dp),
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "Book your Scrap Pick-up",
                                        style = TextStyle(
                                            color = Color.Black
                                        )
                                    )
                                    Text(
                                        modifier = Modifier.padding(top = 10.dp),
                                        text = "See the price and then book our service",
                                        style = TextStyle(
                                            color = Color.Black
                                        )
                                    )
                                }
                                Image(
                                    painter = painterResource(id = R.drawable.intro2),
                                    contentDescription = "android image",
                                    modifier = Modifier
                                        .size(110.dp)
                                        .align(Alignment.CenterVertically)
                                        .padding(end = 15.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}