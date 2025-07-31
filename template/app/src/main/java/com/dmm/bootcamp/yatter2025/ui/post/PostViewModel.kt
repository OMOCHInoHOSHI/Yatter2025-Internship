package com.dmm.bootcamp.yatter2025.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2025.common.navigation.Destination
import com.dmm.bootcamp.yatter2025.common.navigation.PopBackDestination
import com.dmm.bootcamp.yatter2025.domain.service.GetLoginUserService
import com.dmm.bootcamp.yatter2025.usecase.post.PostYweetUseCase
import com.dmm.bootcamp.yatter2025.usecase.post.PostYweetUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostViewModel(
    private val postYweetUseCase: PostYweetUseCase,
    private val getLoginUserService: GetLoginUserService,
) : ViewModel() {

    // Uistate
    private val _uiState: MutableStateFlow<PostUiState> = MutableStateFlow(PostUiState.empty())
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    // 画面遷移
    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()


    // 画面の初期起動時にユーザー情報取得する
    fun onCreate() {

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // ログイン済みのユーザーを取得
            val me = getLoginUserService.execute()

            val snapshotBindingModel = uiState.value.bindingModel

            // 必要なアバター画像の情報のみをUiStateに更新
            // 読み込みを行うため、isLoadingも更新してローディング表示する
            _uiState.update {
                it.copy(
                    bindingModel = snapshotBindingModel.copy(avatarUrl = me?.avatar?.toString()),
                    isLoading = false,
                )
            }
        }


    }

    // Yweetの内容を書き換えた時に呼び出される
    fun onChangedYweetText(yweetText: String) {
        _uiState.update { it.copy(bindingModel = uiState.value.bindingModel.copy(yweetText = yweetText)) }
    }

    // 投稿ボタンを押下した時
    fun onClickPost() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = postYweetUseCase.execute(
                content = uiState.value.bindingModel.yweetText,
                attachmentList = listOf()
            )
            when (result) {
                // 投稿完了したら画面を戻る
                PostYweetUseCaseResult.Success -> {
                    _destination.value = PopBackDestination
                }
                is PostYweetUseCaseResult.Failure -> {
                    // エラー表示
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }


    // ツイート画面

    // パブリックタイムライン画面に戻るために表示するボタン押下時
    fun onClickNavIcon() {
        // 値を流すだけ
        _destination.value = PopBackDestination
    }

    // 画面遷移が終わったあとにDestinationを初期化する
    fun onCompleteNavigation() {
        _destination.value = null
    }

}