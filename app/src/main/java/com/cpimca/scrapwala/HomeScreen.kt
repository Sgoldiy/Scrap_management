package com.cpimca.scrapwala

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Request : Screen("request")
    object Account : Screen("account")
}

@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(route = Screen.Home.route) {
            HomeFregment(navController = navController, onItemSelected = {
            })
        }
        composable(route = Route.ScrapService) {
            ScrapService(navController = navController)
        }
        composable(route = Route.CategoryService) {
            CategoryService(navController = navController)
        }
        composable(route = Route.MonthlyPickUpService) {
            MonthlyPickUpService(navController = navController)
        }
        composable(route = Route.MonthlyPickUpServiceUser) {
            MonthlyPickUpServiceUser(navController = navController)
        }
        composable(route = Route.MonthlyPickUpServiceConfirmUser) {
            MonthlyPickUpServiceConfirmUser(navController = navController)
        }
        composable(route = Route.CategoryServiceUser) {
            CategoryServiceUser(navController = navController)
        }
        composable(route = Route.CategoryServiceConfirmUser) {
            CategoryServiceConfirmUser(navController = navController)
        }
        composable(route = Route.ScrapServiceUser) {
            ScrapServiceUser(navController = navController)
        }
        composable(route = Route.ScrapServiceConfirmUser) {
            ScrapServiceConfirmUser(navController = navController)
        }
        composable(route = Route.MonthlyPickUpServiceComplain){
            MonthlyPickUpServiceComplain(navController = navController)
        }
        composable(route = Route.MonthlyCategoryServiceComplain){
            MonthlyCategoryServiceComplain(navController = navController)
        }
        composable(route = Route.MonthlyScrapServiceComplain){
            MonthlyScrapServiceComplain(navController = navController)
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun CustomTopAppBar(
    title: String,
    onNavigationIconClick: () -> Unit,
    navController: NavHostController,

    ) {
    val db = Firebase.firestore
    val currentUser = FirebaseAuth.getInstance().currentUser
    var check by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var isDrawerOpen by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF0E5204D19))
    ) {
        IconButton(
            onClick = {
                isDrawerOpen = true
            },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                tint = Color.White
            )
        }
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (isDrawerOpen) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { isDrawerOpen = false }
            ) {
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .width(300.dp)
                            .background(Color.White)
                            .clickable { isDrawerOpen = true },
                        contentAlignment = Alignment.TopStart
                    ) {
                        // Display the fetched data here
                        if (check) {
                            Column(
                                modifier = Modifier
                                    .padding(top = 200.dp, start = 20.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Welcome! \n $name",
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    style = TextStyle(
                                        color = Color(0xFF0E5204D19),
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                                Text(
                                    text = "scrap OUT money IN!",
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                                Divider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp),
                                    color = Color(0xFF0E5204D19)
                                )
                                Text(
                                    text = "$email",
                                    style = TextStyle(
                                        color = Color(0xFF0E5204D19),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Light
                                    ),
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        } else {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(16.dp),
                                color = Color(0xFF0E5204D19)
                            )
                        }
                        // Fetch data from Firestorm
                        LaunchedEffect(currentUser) {
                            currentUser?.let { user ->
                                db.collection("users")
                                    .whereEqualTo("email", user.email) // Filter by user's email
                                    .get()
                                    .addOnSuccessListener { result ->
                                        for (document in result) {
                                            name = document.get("name") as String
                                            email = document.get("email") as String
                                            phone = document.get("phone") as String
                                        }
                                        check = true
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(
                                            context,
                                            exception.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .background(Color.White),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            val imageLoader = ImageLoader.Builder(LocalContext.current)
                                .components {
                                    if (Build.VERSION.SDK_INT >= 28) {
                                        add(ImageDecoderDecoder.Factory())
                                    } else {
                                        add(GifDecoder.Factory())
                                    }
                                }
                                .build()

                            Image(
                                painter = rememberAsyncImagePainter(
                                    R.drawable.scrapwala,
                                    imageLoader
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(150.dp)
                                    .padding(top = 20.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 100.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.round_logout_24),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = Color(0xFF0E5204D19)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            ClickableText(
                                text = AnnotatedString("Logout"),
                                style = TextStyle(
                                    color = Color(0xFF0E5204D19),
                                ),
                                onClick = {
                                    FirebaseAuth.getInstance().signOut()
                                    navController.navigate(Route.LoginScreen) {
                                        popUpTo(Route.HomeScreen) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    onItemSelected: (Int) -> Unit,
) {
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    val items = listOf(
        BottomNavItem(
            title = "  Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        BottomNavItem(
            title = "   Price",
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart,
        ),

        BottomNavItem(
            title = "Workers",
            selectedIcon = Icons.Filled.AccountBox,
            unselectedIcon = Icons.Outlined.AccountBox,
        )
    )
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Scrapwala",
                onNavigationIconClick = { navController.navigateUp() },
                navController = navController
            )
        },
        bottomBar = {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                BottomNavigation(
                    backgroundColor = Color(0xFF0E5204D19),
                    modifier = Modifier.height(65.dp),
                ) {
                    items.forEachIndexed { index, item ->
                        val isSelected = selectedItem == index

                        BottomNavigationItem(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .wrapContentSize()
                                .padding(bottom = 10.dp)
                                .background(
                                    color = if (isSelected) Color.White else Color.Transparent,
                                    shape = RoundedCornerShape(
                                        bottomStart = 16.dp,
                                        bottomEnd = 16.dp
                                    )
                                ),
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = null,
                                    tint = if (isSelected) Color(0xFF0E5204D19) else Color.White,
                                    modifier = Modifier
                                        .padding(top = 20.dp)
                                        .size(24.dp)
                                )
                            },
                            label = {
                                AnimatedVisibility(
                                    modifier = Modifier
                                        .align(Alignment.Bottom),
                                    visible = isSelected,
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    Text(
                                        modifier = Modifier.width(45.dp),
                                        text = item.title,
                                        style = TextStyle(
                                            fontSize = 12.sp
                                        ),
                                        color = Color(0xFF0E5204D19)
                                    )
                                }
                            },
                            selected = isSelected,
                            onClick = {
                                selectedItem = index
                                onItemSelected(index)
                            },
                        )
                    }
                }
            }
        }
    ) {
        when (selectedItem) {
            0 -> Navigation()
            1 -> RequestFregment(
                navController = navController,
                onItemSelected = { selectedItem = it }
            )

            2 -> AccountFregment(
                navController = navController,
                onItemSelected = { selectedItem = it }
            )
        }
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Transparent

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}

