package com.dmm.bootcamp.yatter2025.ui.login

import com.dmm.bootcamp.yatter2025.ui.login.bindingmodel.LoginBindingModel

data class LoginUiState(
    val loginBindingModel: LoginBindingModel,
    val isLoading: Boolean,
)