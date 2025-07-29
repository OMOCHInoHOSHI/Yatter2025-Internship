package com.dmm.bootcamp.yatter2025.ui.timeline

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


// StateFlowは保持している値がImmutableになり、初期化以降にデータの更新ができないため、更新が可能なMutableStateFlowと併せて利用します。

// このような定義の仕方をbacking propertiesといいます。
//ViewModelなどのクラス内で処理を行い更新する値があり、その値を外部にも公開したいときによく用いられます。
class PublicTimelineViewModel : ViewModel() {
    // TODO
    private val _uiState: MutableStateFlow<PublicTimelineUiState> =
        MutableStateFlow(PublicTimelineUiState.empty())
    val uiState: StateFlow<PublicTimelineUiState> = _uiState.asStateFlow()
}