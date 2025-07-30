package com.dmm.bootcamp.yatter2025.ui.login

import androidx.lifecycle.ViewModel
import com.dmm.bootcamp.yatter2025.usecase.login.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



// ログイン
class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    // uiStateを監視し、外から変更できないようにすルために２つに分けてる

    // _uiStateは、ViewModel内でのみ変更されるMutableStateFlow
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.empty())

    // uiStateは、StateFlowとして公開され、外部から変更できないようにしている
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
}