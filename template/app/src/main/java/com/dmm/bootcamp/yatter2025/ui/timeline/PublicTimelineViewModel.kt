package com.dmm.bootcamp.yatter2025.ui.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2025.domain.repository.YweetRepository
import com.dmm.bootcamp.yatter2025.ui.timeline.bindingmodel.converter.YweetConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


// StateFlowは保持している値がImmutableになり、初期化以降にデータの更新ができないため、更新が可能なMutableStateFlowと併せて利用します。

// このような定義の仕方をbacking propertiesといいます。
//ViewModelなどのクラス内で処理を行い更新する値があり、その値を外部にも公開したいときによく用いられます。
class PublicTimelineViewModel (
    // Yweetの一覧を取得するためにYweetRepositoryを依存関係に追加
    private val yweetRepository: YweetRepository,
    ) : ViewModel() {
    private val _uiState: MutableStateFlow<PublicTimelineUiState> =
        MutableStateFlow(PublicTimelineUiState.empty())
    val uiState: StateFlow<PublicTimelineUiState> = _uiState.asStateFlow()

    // 続いてViewModel内にYweetRepositoryからYweetの一覧を取得するメソッド
    private suspend fun fetchPublicTimeline(){
        // YweetRepositoryからYweet一覧を取得
        val yweetList = yweetRepository.findAllPublic()

        // PublicTimeline内のyweetListを更新
        _uiState.update {
            // data classの値の一部のみを更新することができます
            it.copy(
                yweetList = YweetConverter.convertToBindingModel(yweetList),
            )
        }
    }

    // 今回の画面では画面を表示するたびに最新のリストを取得しておきたい関数
    fun onResume() {
        viewModelScope.launch { // ViewModelのライフサイクルに合わせたスコープのcoroutine起動
            _uiState.update { it.copy(isLoading = true) } // UiStateをローディング状態にする
            fetchPublicTimeline() // fetchPublicTimeline()メソッドを呼び出しYweet一覧を更新
            _uiState.update { it.copy(isLoading = false) } // UiStateのローディング状態を解除する
        }
    }
}