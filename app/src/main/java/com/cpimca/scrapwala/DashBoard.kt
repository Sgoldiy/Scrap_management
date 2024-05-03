package com.cpimca.scrapwala

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DashBoard(navController: NavHostController) {

    val pagerState = rememberPagerState(initialPage = 0)

    val imageSlider = listOf(
        painterResource(id = R.drawable.img_banner1),
        painterResource(id = R.drawable.img_banner2),
        painterResource(id = R.drawable.img_banner3)
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(Color(0xFF0E5204D19)),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_dashboard_24),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Dashboard",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp, // Adjust as needed
                    color = Color.White // Adjust color as needed
                )
            )
        }
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
                    .padding(top = 70.dp)
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
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
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
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 250.dp, bottom = 50.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(140.dp)
                            .width(130.dp),
                        colors = CardDefaults.cardColors(Color(0xFF0E5204D19)),
                        elevation = CardDefaults.cardElevation(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .height(133.dp)
                                .width(123.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    navController.navigate(Route.AddWorker)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.addworker),
                                contentDescription = "android image",
                                modifier = Modifier.size(100.dp)
                            )
                            Text(
                                text = "Add worker",
                                style = TextStyle(
                                    color = Color(0xFF0E5204D19)
                                )
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(140.dp)
                            .width(130.dp),
                        colors = CardDefaults.cardColors(Color(0xFF0E5204D19)),
                        elevation = CardDefaults.cardElevation(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .height(133.dp)
                                .width(123.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    navController.navigate(Route.ManageWorker)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.manageworker),
                                contentDescription = "android image",
                                modifier = Modifier.size(100.dp)
                            )
                            Text(
                                text = "Manage Worker",
                                style = TextStyle(
                                    color = Color(0xFF0E5204D19)
                                )
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(140.dp)
                            .width(130.dp),
                        colors = CardDefaults.cardColors(Color(0xFF0E5204D19)),
                        elevation = CardDefaults.cardElevation(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .height(133.dp)
                                .width(123.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    navController.navigate(Route.CategoryServiceAdmin)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.dustbin),
                                contentDescription = "android image",
                                modifier = Modifier.size(100.dp)
                            )
                            Text(
                                text = "Category request",
                                style = TextStyle(
                                    color = Color(0xFF0E5204D19)
                                )
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(140.dp)
                            .width(130.dp),
                        colors = CardDefaults.cardColors(Color(0xFF0E5204D19)),
                        elevation = CardDefaults.cardElevation(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .height(133.dp)
                                .width(123.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    navController.navigate(Route.CategoryServiceConfirmAdmin)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.requestconfirm),
                                contentDescription = "android image",
                                modifier = Modifier.size(100.dp)
                            )
                            Text(
                                text = "Confirm Request",
                                style = TextStyle(
                                    color = Color(0xFF0E5204D19)
                                )
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(140.dp)
                            .width(130.dp),
                        colors = CardDefaults.cardColors(Color(0xFF0E5204D19)),
                        elevation = CardDefaults.cardElevation(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .height(133.dp)
                                .width(123.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    navController.navigate(Route.ScrapServiceAdmin)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.cleaningservice),
                                contentDescription = "android image",
                                modifier = Modifier.size(100.dp)
                            )
                            Text(
                                text = "scrap service",
                                style = TextStyle(
                                    color = Color(0xFF0E5204D19)
                                )
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(140.dp)
                            .width(130.dp),
                        colors = CardDefaults.cardColors(Color(0xFF0E5204D19)),
                        elevation = CardDefaults.cardElevation(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .height(133.dp)
                                .width(123.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    navController.navigate(Route.ScrapServiceConfirmAdmin)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.confirmservice),
                                contentDescription = "android image",
                                modifier = Modifier.size(100.dp)
                            )
                            Text(
                                text = "confirm service",
                                style = TextStyle(
                                    color = Color(0xFF0E5204D19)
                                )
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(140.dp)
                            .width(130.dp),
                        colors = CardDefaults.cardColors(Color(0xFF0E5204D19)),
                        elevation = CardDefaults.cardElevation(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .height(133.dp)
                                .width(123.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    navController.navigate(Route.MonthlyPickUpServiceAdmin)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.pickuprequest),
                                contentDescription = "android image",
                                modifier = Modifier.size(100.dp)
                            )
                            Text(
                                text = "pick-up request",
                                style = TextStyle(
                                    color = Color(0xFF0E5204D19)
                                )
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(140.dp)
                            .width(130.dp),
                        colors = CardDefaults.cardColors(Color(0xFF0E5204D19)),
                        elevation = CardDefaults.cardElevation(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .height(133.dp)
                                .width(123.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    navController.navigate(Route.MonthlyPickUpServiceConfirmAdmin)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.confirmpickup),
                                contentDescription = "android image",
                                modifier = Modifier.size(100.dp)
                            )
                            Text(
                                text = "Confirm pick-up",
                                style = TextStyle(
                                    color = Color(0xFF0E5204D19)
                                )
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .padding(start = 40.dp, top = 8.dp)
                        .height(140.dp)
                        .width(130.dp),
                    colors = CardDefaults.cardColors(Color(0xFF0E5204D19)),
                    elevation = CardDefaults.cardElevation(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .height(133.dp)
                            .width(123.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .clickable {
                                navController.navigate(Route.UserComplainAdmin)
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,

                        ) {
                        Image(
                            painter = painterResource(id = R.drawable.intro1),
                            contentDescription = "android image",
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = "User complains",
                            style = TextStyle(
                                color = Color(0xFF0E5204D19)
                            )
                        )
                    }
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 765.dp)
                .fillMaxWidth()
                .height(55.dp)
                .background(Color(0xFF0E5204D19)),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.round_logout_24),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                modifier = Modifier
                    .clickable {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(Route.LoginScreen) {
                            popUpTo(Route.DashBoard) {
                                inclusive = true
                            }
                        }

                    },
                text = "Logout",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = Color.White
                )
            )
        }
    }
}