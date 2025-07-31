package com.dmm.bootcamp.yatter2025.ui.timeline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2025.ui.LocalNavController
import org.koin.androidx.compose.getViewModel


// ViewModelから状態を取り出し、Templateコンポーザブルに渡す
@Composable
fun PublicTimelinePage(
    //getViewmodel はDMMが定義
    publicTimelineViewModel: PublicTimelineViewModel = getViewModel(),
) {
    // UiStateを抜き出し
    // publicTimelineViewModel#uiStateの変更を監視する
    val uiState by publicTimelineViewModel.uiState.collectAsStateWithLifecycle()


    // destinationを取得してツイート画面へ遷移
    val destination by publicTimelineViewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    LaunchedEffect(destination) {
        destination?.let {
            it.navigate(navController)
            publicTimelineViewModel.onCompleteNavigation()
        }
    }


    // ライフサイクルイベントで呼び出し
    // LifecycleEventEffectを使うことによって、指定したライフサイクルのイベントごとに処理を実行
    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        publicTimelineViewModel.onResume()
    }

    PublicTimelineTemplate(
        yweetList = uiState.yweetList,
        isLoading = uiState.isLoading,
        isRefreshing = uiState.isRefreshing,
        onRefresh = publicTimelineViewModel::onRefresh, // onRefreshはViewModelのメソッドになるため、UiStateからは取得できません は関数オブジェクトとして渡す ::を使う
        onClickPost = publicTimelineViewModel::onClickPost,
    )
}