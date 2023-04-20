package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.*
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
            Navigation()
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
        composable(BottomNavigationItems.SideNav.Route){
            val context = LocalContext.current
            val intent = Intent(context, SideNavigationActivity::class.java)
            context.startActivity(intent)
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
        BottomNavigationItems.Profile,
        BottomNavigationItems.SideNav
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
//adding search bar for countries
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController = navController)
        }
        composable(
            "details/{countryName}",
            arguments = listOf(navArgument("search") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("search")?.let { countryName ->
                DetailsScreen(countryName = countryName)
            }
        }
    }
}

@Composable
fun TopSearchBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
        backgroundColor = colorResource(id = R.color.black),
        contentColor = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopSearchBar()
}
@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = colorResource(id = androidx.core.R.color.notification_icon_bg_color),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    SearchView(textState)
}
//@Composable
//fun CountryListItem(countryText: String, onItemClick: (String) -> Unit) {
//    Row(
//        modifier = Modifier
//            .clickable(onClick = { onItemClick(countryText) })
//            .background(colorResource(id = R.color.black))
//            .height(57.dp)
//            .fillMaxWidth()
//            .padding(PaddingValues(8.dp, 16.dp))
//    ) {
//        Text(text = countryText, fontSize = 18.sp, color = Color.White)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun CountryListItemPreview() {
//    CountryListItem(countryText = "United States 🇺🇸", onItemClick = { })
//}
//@Composable
//fun CountryList(navController: NavController, state: MutableState<TextFieldValue>) {
//    val countries = getListOfCountries()
//    var filteredCountries: ArrayList<String>
//    LazyColumn(modifier = Modifier.fillMaxWidth()) {
//        val searchedText = state.value.text
//        filteredCountries = if (searchedText.isEmpty()) {
//            countries
//        } else {
//            val resultList = ArrayList<String>()
//            for (country in countries) {
//                if (country.lowercase(Locale.getDefault())
//                        .contains(searchedText.lowercase(Locale.getDefault()))
//                ) {
//                    resultList.add(country)
//                }
//            }
//            resultList
//        }
//        items(filteredCountries) { filteredCountry ->
//            CountryListItem(
//                countryText = filteredCountry,
//                onItemClick = { selectedCountry ->
//                    /* Add code later */
//                    navController.navigate("details/$selectedCountry") {
//                        // Pop up to the start destination of the graph to
//                        // avoid building up a large stack of destinations
//                        // on the back stack as users select items
//                        popUpTo("main") {
//                            saveState = true
//                        }
//                        // Avoid multiple copies of the same destination when
//                        // reselecting the same item
//                        launchSingleTop = true
//                        // Restore state when reselecting a previously selected item
//                        restoreState = true
//                    }
//                }
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun CountryListPreview() {
//    val navController = rememberNavController()
//    val textState = remember { mutableStateOf(TextFieldValue("")) }
//    CountryList(navController = navController, state = textState)
//}
//
//fun getListOfCountries(): ArrayList<String> {
//    val isoCountryCodes = Locale.getISOCountries()
//    val countryListWithEmojis = ArrayList<String>()
//    for (countryCode in isoCountryCodes) {
//        val locale = Locale("", countryCode)
//        val countryName = locale.displayCountry
//        val flagOffset = 0x1F1E6
//        val asciiOffset = 0x41
//        val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
//        val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
//        val flag =
//            (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
//        countryListWithEmojis.add("$countryName $flag")
//    }
//    return countryListWithEmojis
//}
@Composable
fun MainScreen(navController: NavController) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Column {
        SearchView(textState)
//        CountryList(navController = navController, state = textState)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    MainScreen(navController = navController)
}
@Composable
fun DetailsScreen(countryName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = countryName,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 22.sp
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DetailsScreenPreview() {
//    DetailsScreen("United States 🇺🇸")
//}

