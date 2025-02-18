package com.example.home_screen.content.models

enum class Tabs(val nameTab: String,val index: Int) {
    TRENDS("Trends",0),
    SUBSCRIPTIONS("Subscriptions",1);

    companion object {
        const val MAX_HEIGHT = 80
        const val MIN_HEIGHT = 0
    }


}
