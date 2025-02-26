package com.example.uikit.home


enum class TabContents(val nameTab: String, val index: Int) {
    TRENDS("Trends",0),
    SUBSCRIPTIONS("Subscriptions",1);

    companion object {
        const val MAX_HEIGHT = 65
        const val MIN_HEIGHT = 0
    }


}
