package com.dmm.bootcamp.yatter2025.ui.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2025.common.navigation.Destination


// 画面遷移時に必要なパラメータを内包する`Destination`型
class LoginDestination : Destination(ROUTE) {
    companion object {
        private const val ROUTE = "login"

        fun createNode(builder: NavGraphBuilder) {
            builder.composable(ROUTE) {
                LoginPage()
            }
        }
    }
}