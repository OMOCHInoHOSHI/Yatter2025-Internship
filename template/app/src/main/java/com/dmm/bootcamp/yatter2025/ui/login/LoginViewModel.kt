package com.dmm.bootcamp.yatter2025.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2025.common.navigation.Destination
import com.dmm.bootcamp.yatter2025.domain.model.Password
import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.ui.timeline.PublicTimelineDestination
import com.dmm.bootcamp.yatter2025.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2025.usecase.login.LoginUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


// ログイン
class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    // uiStateを監視し、外から変更できないようにすルために２つに分けてる

    // _uiStateは、ViewModel内でのみ変更されるMutableStateFlow
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.empty())

    // uiStateは、StateFlowとして公開され、外部から変更できないようにしている
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    //画面遷移処理はViewModel内では実施できないため、UI側に画面遷移することを通達する手段
    private val _destination = MutableStateFlow<Destination?>(null)
    // 参照される
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

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
    fun onClickLogin() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) } // ローディング状態にする1

            val snapBindingModel = uiState.value.loginBindingModel
            when (
                val result =
                    loginUseCase.execute(
                        Username(snapBindingModel.username),
                        Password(snapBindingModel.password),
                    ) // ログイン処理(LoginUseCase)実行2
            ) {
                is LoginUseCaseResult.Success -> {
                    // ログイン処理成功したらパブリックタイムライン画面へ遷移3
                    // パブリックタイムライン画面に遷移する処理の追加
//                    _destination.value = PublicTimelineDestination()

                    // 戻るでログイン画面に戻らないようにする
                    _destination.value = PublicTimelineDestination {
                        popUpTo(LoginDestination().route) {
                            inclusive = true
                        }
                    }
                }

                is LoginUseCaseResult.Failure -> {
                    // ログイン処理失敗したらエラー表示4
                    // エラー表示
                }
            }

            _uiState.update { it.copy(isLoading = false) } // ローディング状態を解除する5
        }
    }

    // _destinationにユーザー登録画面のDestinationを渡します
    fun onClickRegister() {
//         _destination.value = RegisterUserDestination()
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }

}