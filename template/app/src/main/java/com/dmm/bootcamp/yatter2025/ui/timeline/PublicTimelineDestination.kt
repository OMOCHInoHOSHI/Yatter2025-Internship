package com.dmm.bootcamp.yatter2025.ui.timeline

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2025.common.navigation.Destination

// 画面遷移時に必要なパラメータを内包する`Destination`型
class PublicTimelineDestination(
    builder: (NavOptionsBuilder.() -> Unit)? = null,
) : Destination(ROUTE, builder) {
    companion object {
        private const val ROUTE = "publicTimeline"

        fun createNode(builder: NavGraphBuilder) {
            builder.composable(ROUTE) {
                PublicTimelinePage()
            }
        }
    }
}