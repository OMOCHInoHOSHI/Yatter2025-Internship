package com.dmm.bootcamp.yatter2025.ui.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2025.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
fun NewuserPage(
    newuserViewModel: RegisterUserViewModel = getViewModel(),
){

    val uiState: NewuserUiState by newuserViewModel.uiState.collectAsStateWithLifecycle()

    NewuserTemplate(
        userName = uiState.newuserBindingModel.username,
        onChangedUserName = newuserViewModel::onChangedUsername,//関数オブジェクトを渡す
        password = uiState.newuserBindingModel.password,
        onChangedPassword = newuserViewModel::onChangedPassword,
        isEnableLogin = uiState.isEnabelnewUser,
        isLoading = uiState.isloding,
        onClickCreateNewuser = newuserViewModel::onClickCreateNewuser,
        onClickRegister = newuserViewModel::onClickRegister,    //ログインページへの画面遷移
    )

        // destinationのStateFlowをLoginPage側で購読
        val destination by newuserViewModel.destination.collectAsStateWithLifecycle()
        val navController = LocalNavController.current

        LaunchedEffect(destination) {
            destination?.let {
                it.navigate(navController)
                newuserViewModel.onCompleteNavigation()
            }
        }

}