package com.dmm.bootcamp.yatter2025.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginPage(
    loginViewModel: LoginViewModel = getViewModel(),
) {
    val uiState: LoginUiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    LoginTemplate(
        userName = uiState.loginBindingModel.username,
        onChangedUserName = loginViewModel::onChangedUsername,  //関数オブジェクトを渡す
        password = uiState.loginBindingModel.password,
        onChangedPassword = loginViewModel::onChangedPassword,  //関数オブジェクトを渡す
        isEnableLogin = uiState.isEnableLogin,
        isLoading = uiState.isLoading,
        onClickLogin = loginViewModel::onClickLogin,
        onClickRegister = loginViewModel::onClickRegister,
    )
}