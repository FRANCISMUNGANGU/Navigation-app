package com.example.myapplication

sealed class BottomNavigationItems(var Route: String, var Icon: Int, var Title: String ){
    object Home : BottomNavigationItems("home",R.drawable.baseline_home_24,"Home")
    object About : BottomNavigationItems("about",R.drawable.baseline_help_outline_24,"About")
    object Profile : BottomNavigationItems("profile",R.drawable.baseline_supervisor_account_24,"Profile")
}
