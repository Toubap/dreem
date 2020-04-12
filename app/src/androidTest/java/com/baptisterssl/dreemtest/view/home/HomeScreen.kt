package com.baptisterssl.dreemtest.view.home

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.progress.KProgressBar
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.baptisterssl.dreemtest.R
import org.hamcrest.Matcher

class HomeScreen : Screen<HomeScreen>() {

    val progressPlayer = KProgressBar { withId(R.id.progressPlayer) }
    val viewPlayer = KView { withId(R.id.viewPlayer) }
    val viewPlayerError = KView { withId(R.id.viewPlayerError) }

    class CarouselItem(parent: Matcher<View>) : KRecyclerItem<Any>(parent) {
        val cardViewCarousel = KView { withId(R.id.cardViewCarousel) }
    }

    val recyclerViewCarousel = KRecyclerView({ withId(R.id.recyclerViewCarousel) },
        itemTypeBuilder = { itemType(::CarouselItem) })
}