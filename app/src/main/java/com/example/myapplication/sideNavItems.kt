package com.example.myapplication

sealed class sideNavItems(var Route: String, var Icon: Int, var Title: String ){
    object Home : sideNavItems("home",R.drawable.baseline_home_24,"Home")
    object About : sideNavItems("about",R.drawable.baseline_help_outline_24,"About")
    object Profile : sideNavItems("profile",R.drawable.baseline_supervisor_account_24,"Profile")
    object BottomNav : sideNavItems("BottomNav",R.drawable.baseline_supervisor_account_24,"BottomNav")

    // can have more than 6 items
}

