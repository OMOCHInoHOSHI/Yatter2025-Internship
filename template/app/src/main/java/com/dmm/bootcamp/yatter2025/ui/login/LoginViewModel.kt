package com.dmm.bootcamp.yatter2025.ui.login

import androidx.lifecycle.ViewModel
import com.dmm.bootcamp.yatter2025.domain.model.Password
import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.usecase.login.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


// ログイン
class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    // uiStateを監視し、外から変更できないようにすルために２つに分けてる

    // _uiStateは、ViewModel内でのみ変更されるMutableStateFlow
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.empty())

    // uiStateは、StateFlowとして公開され、外部から変更できないようにしている
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()



    // メソッド定義

    // テキストボックス入力時
    fun onChangedUsername(username: String) {
        val snapshotBindingModel = uiState.value.loginBindingModel
        _uiState.update {
            it.copy(

                // バリデーション
                // ドメイン層で実装される
                validUsername = Username(username).validate(),
                loginBindingModel = snapshotBindingModel.copy(
                    username = username
                )
            )
        }
    }

    fun onChangedPassword(password: String) {
        val snapshotBindingModel = uiState.value.loginBindingModel
        _uiState.update {
            it.copy(
                // バリデーション
                validPassword = Password(password).validate(),
                loginBindingModel = snapshotBindingModel.copy(
                    password = password
                )
            )
        }
    }


    // ボタン押下時
    fun onClickLogin() {}

    fun onClickRegister() {}

    fun onCompleteNavigation() {}

}