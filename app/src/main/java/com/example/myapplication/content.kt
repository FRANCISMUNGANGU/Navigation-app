package com.example.myapplication

import androidx.annotation.DrawableRes

val cat1 = Content(
    id = 0,
    title = "Cute cat",
    thumbnail = R.drawable.cat1,
    body = "Meow"
)
val cat2 = Content(
    id = 1,
    title = "Cute cat 2",
    thumbnail = R.drawable.cat2,
    body = "Meow"
)
val cat3 = Content(
    id = 2,
    title = "Cute cat 3",
    thumbnail = R.drawable.cat3,
    body = "Meow"
)
val cat4= Content(
    id = 3,
    title = "Cute cat 4",
    thumbnail = R.drawable.cat4,
    body = "Meow"
)

data class Content(
    val id: Long,
    val title: String,
    @DrawableRes val thumbnail:Int,
    val body:String
)
//list
val allArticles = listOf(cat1, cat2, cat3, cat4)