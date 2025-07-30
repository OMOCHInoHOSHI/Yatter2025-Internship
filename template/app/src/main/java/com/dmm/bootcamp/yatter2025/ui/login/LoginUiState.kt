package com.dmm.bootcamp.yatter2025.ui.login

import com.dmm.bootcamp.yatter2025.ui.login.bindingmodel.LoginBindingModel

data class LoginUiState(
    val loginBindingModel: LoginBindingModel,
    val isLoading: Boolean, //ローディング
    val validUsername: Boolean, //ゆーざーねーむ入力
    val validPassword: Boolean, //パスワード入力されたか
){
    // ユーザーフレンドリー
    // ログイン実行可能かのフラグ
    val isEnableLogin: Boolean = validUsername && validPassword

    // クラス内に作成されるSingleton(指定したクラスのインスタンスが1つしか存在しないことを保証する)
    companion object{
        fun empty(): LoginUiState = LoginUiState(
            // LoginBindingModelの定義を使う
            loginBindingModel = LoginBindingModel(
                username = "",
                password = ""
            ),
            isLoading = false,
            validUsername = false,
            validPassword = false,
        )
    }

}