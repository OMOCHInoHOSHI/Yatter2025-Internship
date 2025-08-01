package com.dmm.bootcamp.yatter2025.ui.signup

import com.dmm.bootcamp.yatter2025.ui.signup.bindingmodel.NewuserBindingModel


// UiStateはUI全体の状態を管理

data class NewuserUiState (
    val newuserBindingModel:  NewuserBindingModel,
    val isloding: Boolean,
    val validUsername: Boolean, //ゆーざーねーむ入力
    val validPassword: Boolean, //パスワード入力されたか
){
    // ユーザーフレンドリー
    // 新規登録実行可能か
    val isEnabelnewUser: Boolean = validUsername && validUsername

     // クラス内に作成されるSingleton(指定したクラスのインスタンスが1つしか存在しないことを保証する)
    companion object{
        // 初期化用
         fun empty(): NewuserUiState = NewuserUiState(
             newuserBindingModel = NewuserBindingModel(
                 username = "",
                 password = ""
             ),
             isloding = false,
             validUsername = false,
             validPassword = false,
         )
    }

}