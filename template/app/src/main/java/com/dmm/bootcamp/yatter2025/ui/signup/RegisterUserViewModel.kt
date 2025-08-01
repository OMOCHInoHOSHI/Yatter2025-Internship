package com.dmm.bootcamp.yatter2025.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2025.common.navigation.Destination
import com.dmm.bootcamp.yatter2025.domain.model.Password
import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.ui.timeline.PublicTimelineDestination
import com.dmm.bootcamp.yatter2025.usecase.register.RegisterUserUseCase
import com.dmm.bootcamp.yatter2025.usecase.register.RegisterUserUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// UI表示に必要な実装を行う


class RegisterUserViewModel(
    private val newuserUseCase: RegisterUserUseCase
):ViewModel(){

    // uiの情報を保持
    private val _uiState: MutableStateFlow<NewuserUiState> = MutableStateFlow(NewuserUiState.empty())
    // uiStateは、StateFlowとして公開され、外部から変更できないようにしている
    val uiState: StateFlow<NewuserUiState> = _uiState.asStateFlow()

    //画面遷移処理はViewModel内では実施できないため、UI側に画面遷移することを通達する手段
    private val _destination = MutableStateFlow<Destination?>(null)
    // 参照される
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    // テキストボックス入力時
    fun onChangedUsername(username: String) {
        // (UI) の状態のスナップショット（静的なコピー）
        val snapshotBindingModel = uiState.value.newuserBindingModel

        _uiState.update {
            it.copy(
                // バリデーション
                validUsername = Username(username).validate(),

                // usernameだけを更新、BindingModelのusernameを更新
                newuserBindingModel = snapshotBindingModel.copy(
                    username = username
                )
            )
        }

    }

    // テキストボックス入力時
    fun onChangedPassword(password: String) {
        // uiの状態のスナップショット
        val snapshotBindingModel = uiState.value.newuserBindingModel

        _uiState.update {
            it.copy(
                // Pssswordクラスのvalidateメソッドがバリデーションチェック
                validPassword = Password(password).validate(),

                newuserBindingModel = snapshotBindingModel.copy(
                    password = password
                )
            )
        }
    }



//    新規登録ボタン押下時
    fun onClickNewuser(){

        // ViewModel 内で非同期処理を安全に実行するための Kotlin コルーチン構文 ライフサイクル連動機能など
        viewModelScope.launch {

            // 更新
            _uiState.update {
                it.copy( isloding = true )
            }

        val snapBindingModel = uiState.value.newuserBindingModel

        when(
            val result =
                newuserUseCase.execute(
//                    Username(snapBindingModel.username),
//                    Password(snapBindingModel.password),
                    username = snapBindingModel.username,
                    password = snapBindingModel.password,
                )
        ){

            // resultの型に基づくパターン分岐
            // RegisterUserUseCaseResult.Success 型であれば成功処理
            // 新規登録成功
            is RegisterUserUseCaseResult.Success -> {

//                _destination.value = PublicTimelineDestination()
//                // 画面遷移
                _destination.value = PublicTimelineDestination{
                    popUpTo(RegisterUserDestination().route){
                        inclusive = true
                    }
                }
            }


            is RegisterUserUseCaseResult.Failure.CreateUserError -> TODO()
            RegisterUserUseCaseResult.Failure.EmptyPassword -> TODO()
            RegisterUserUseCaseResult.Failure.EmptyUsername -> TODO()
            RegisterUserUseCaseResult.Failure.InvalidPassword -> TODO()
            is RegisterUserUseCaseResult.Failure.LoginError -> TODO()
        }
        _uiState.update {
            it.copy(isloding = false)
        }

        }
    }

    // _destinationにユーザー登録画面のDestinationを渡します
    fun onClickRegister() {
//         _destination.value = RegisterUserDestination()
    }


    // 初期化
    fun onCompleteNavigation() {
        _destination.value = null
    }


}