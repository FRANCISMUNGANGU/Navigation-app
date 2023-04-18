package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.BottomNavigationItems.About.Route
import com.example.myapplication.ui.theme.MyApplicationTheme

class BottomNavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }


    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun MainScreen(){
    val navController = rememberNavController()

    Scaffold (
        topBar = { TopBar()},
        bottomBar = { BottomNavigationBar(navController)},
        content = {
            Box(modifier = Modifier.padding(it)) {
                Navigation(navController = navController)
            }
        },
        backgroundColor = colorResource(id = R.color.yellow)
            )

}
@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController, startDestination = BottomNavigationItems.Home.Route){
        composable(BottomNavigationItems.Home.Route){
            HomeScreen()
        }
        composable(BottomNavigationItems.About.Route){
            AboutScreen()
        }
        composable(BottomNavigationItems.Profile.Route){
            ProfileScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview(){
    MainScreen()
}

@Composable
fun TopBar (){
    TopAppBar (
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        backgroundColor = colorResource(id = R.color.yellow),
        contentColor = Color.Black
            )
}
@Preview(showBackground = true)
@Composable
fun TopbarPreview(){
    TopBar()
}

@Composable
fun BottomNavigationBar(navController: NavController){
//    Create list of items from sealed bar
//    loop items to create view for itt\
    val items = listOf(
        BottomNavigationItems.Home,
        BottomNavigationItems.About,
        BottomNavigationItems.Profile
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.yellow),
        contentColor = Color.Black
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
// tag active page
        val currentRoute = navBackStackEntry?.destination?.route
//        loop items on bottomnav
        items.forEach {
            BottomNavigationItem(
                selected = false,
                icon = { Icon(painterResource(id = it.Icon), contentDescription = it.Title,)},label = {Text(it.Title)}, alwaysShowLabel = true,
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                onClick ={
                    // handle navigations here
                    navController.navigate(it.Route){
                        navController.graph.startDestinationRoute.let {
                            popUpTo(Route){
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun BottonNaVbar(){
//    BottomNavigationBar()
//}