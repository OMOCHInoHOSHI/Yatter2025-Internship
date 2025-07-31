package com.dmm.bootcamp.yatter2025.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dmm.bootcamp.yatter2025.ui.login.LoginDestination
import com.dmm.bootcamp.yatter2025.ui.post.PostDestination
import com.dmm.bootcamp.yatter2025.ui.timeline.PublicTimelineDestination
import org.koin.androidx.compose.getViewModel



// CompositionLocalという仕組みを使い、NavControllerを引数を経由せずに各Pageに渡しています

// compositionLocalOfでCompositionLocalを定義
// Local~とするのが慣習
val LocalNavController = compositionLocalOf<NavController> {
    error("Not set a NavController!")
}

// NavHostで各Pageへの画面遷移を管理するためのComposable関数をMainAppとして定義
@Composable
fun MainApp(
    mainViewModel: MainViewModel = getViewModel(),
) {
    val navController = rememberNavController()

    // MainViewModelから初期画面のDestinationを取得
    // CheckLoginServiceの実行が終わるまではnullが、実行が終わったらログイン状態に応じたDestinationが取得される
    val startDestination = mainViewModel.startDestination.collectAsState(initial = null).value

    // Lifecycle.Event.ON_CREATE時にmainViewModel.onCreate()を呼び出すようにします
    LifecycleEventEffect(
        event = Lifecycle.Event.ON_CREATE,
    ) {
        // ログイン状態の確認
        mainViewModel.onCreate()
    }
    // 実際にCompositionLocalに対して値をセット
    // セットした値は、CompositionLocalProvider以下のComposable関数から使用することができます
    CompositionLocalProvider(
        LocalNavController provides navController,
    ) {
         // LocalNavController.current を呼び出すと、providesしたnavControllerを取得することができる

        // 取得したstartDestinationをNavHostに渡し、初期画面とする
        // NavHostに各Destinationを登録
        if (startDestination != null) {
            NavHost(
                navController = navController,
                startDestination = startDestination.route
            ) {
                LoginDestination.createNode(this)
                PublicTimelineDestination.createNode(this)
                PostDestination.createNode(this)
            }
        }
    }
}