package com.cpimca.scrapwala

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cpimca.scrapwala.ui.theme.ScrapwalaTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScrapwalaTheme {
                val authViewModel = viewModel<AuthViewModel>()
                authViewModel.initSharedPreferences(LocalContext.current)
                val firebaseAuth = FirebaseAuth.getInstance()
                val isLoggedIn by remember {
                    mutableStateOf(firebaseAuth.currentUser != null)
                }
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = if (isLoggedIn) Route.HomeScreen else if (authViewModel.isLoggedIn) Route.LoginScreen else Route.Splash
                ) {
                    composable(route = Route.Splash) {
                        Splash(navigateToOnBoarding = {
                            navController.navigate(Route.OnBoarding) {
                                popUpTo(Route.Splash) {
                                    inclusive = true
                                }
                            }
                        })
                    }
                    composable(route = Route.LoginScreen) {
                        LoginScreen(navController = navController)
                    }
                    composable(route = Route.OnBoarding) {
                        OnBoarding(navController = navController)
                    }
                    composable(route = Route.RegisterScreen) {
                        RegisterScreen(navController = navController)
                    }
                    composable(route = Route.HomeScreen) {
                        HomeScreen(navController = navController, onItemSelected = { })
                    }
                    composable(route = Route.DashBoard) {
                        DashBoard(navController = navController)
                    }
                    composable(route = Route.MonthlyPickUpServiceAdmin) {
                        UserRequestPickUp(navController = navController)
                    }
                    composable(route = Route.MonthlyPickUpServiceConfirmAdmin) {
                        MonthlyPickUpServiceConfirmAdmin(navController = navController)
                    }
                    composable(route = Route.CategoryServiceAdmin) {
                        UserRequestCategory(navController = navController)
                    }
                    composable(route = Route.CategoryServiceConfirmAdmin) {
                        CategoryServiceConfirmAdmin(navController = navController)
                    }
                    composable(route = Route.ScrapServiceAdmin) {
                        UserRequestScrap(navController = navController)
                    }
                    composable(route = Route.ScrapServiceConfirmAdmin) {
                        ScrapServiceConfirmAdmin(navController = navController)
                    }
                    composable(route = Route.AddWorker){
                        AddWorker(navController = navController)
                    }
                    composable(route = Route.ManageWorker){
                        ManageWorker(navController = navController)
                    }
                    composable(route = Route.MonthlyPickUpServiceComplain){
                        MonthlyPickUpServiceComplain(navController = navController)
                    }
                    composable(route = Route.UserComplainAdmin){
                        UserComplainAdmin(navController = navController)
                    }
                }
            }
        }
    }
}

object Route {
    const val Splash = "Splash"
    const val LoginScreen = "LoginScreen"
    const val OnBoarding = "OnBoarding"
    const val RegisterScreen = "RegisterScreen"
    const val HomeScreen = "HomeScreen"
    const val DashBoard = "DashBoard"
    const val MonthlyPickUpService = "MonthlyPickUpService"
    const val CategoryService = "CategoryService"
    const val ScrapService = "ScrapService"
    const val MonthlyPickUpServiceUser = "MonthlyPickUpServiceUser"
    const val MonthlyPickUpServiceConfirmUser = "MonthlyPickUpServiceConfirmUser"
    const val MonthlyPickUpServiceConfirmAdmin = "MonthlyPickUpServiceConfirmAdmin"
    const val MonthlyPickUpServiceAdmin = "MonthlyPickUpServiceAdmin"
    const val CategoryServiceUser = "CategoryServiceUser"
    const val CategoryServiceAdmin = "CategoryServiceAdmin"
    const val CategoryServiceConfirmAdmin = "CategoryServiceConfirmAdmin"
    const val CategoryServiceConfirmUser = "CategoryServiceConfirmUser"
    const val ScrapServiceUser = "ScrapServiceUser"
    const val ScrapServiceConfirmUser = "ScrapServiceConfirmUser"
    const val ScrapServiceAdmin = "ScrapServiceAdmin"
    const val ScrapServiceConfirmAdmin = "ScrapServiceConfirmAdmin"
    const val AddWorker = "AddWorker"
    const val ManageWorker= "ManageWorker"
    const val MonthlyPickUpServiceComplain = "MonthlyPickUpServiceComplain"
    const val MonthlyCategoryServiceComplain = "MonthlyCategoryServiceComplain"
    const val MonthlyScrapServiceComplain = "MonthlyScrapServiceComplain"
    const val UserComplainAdmin = "UserComplainAdmin"
}


@Composable
fun Splash(
    navigateToOnBoarding: () -> Unit,
) {
    var navigated by remember {
        mutableStateOf(false)
    }

    val offsetY = remember {
        Animatable(0f)
    }

    LaunchedEffect(true) {
        delay(3010)
        navigated = true
        navigateToOnBoarding()
    }

    LaunchedEffect(navigated) {
        if (!navigated) {
            offsetY.animateTo(-810f, animationSpec = tween(durationMillis = 3000))
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier
                .height(321.dp)
                .width(350.dp)
                .offset(y = offsetY.value.dp),
            painter = painterResource(id = R.drawable.splas),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
    }
}
