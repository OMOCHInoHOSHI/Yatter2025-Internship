package com.dmm.bootcamp.yatter2025.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2025.common.navigation.Destination
import com.dmm.bootcamp.yatter2025.domain.service.CheckLoginService
import com.dmm.bootcamp.yatter2025.ui.login.LoginDestination
import com.dmm.bootcamp.yatter2025.ui.timeline.PublicTimelineDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


//アプリ起動時にログイン状態のチェックをして初期画面を決定するためのViewModelの実装
class MainViewModel(
    // ログイン状況を確認するために引数にCheckLoginServiceを受け取ります
    private val checkLoginService: CheckLoginService,
) : ViewModel()
{
    // 初期画面をUIに通知する
    private val _startDestination = MutableStateFlow<Destination?>(null)
    val startDestination: StateFlow<Destination?> = _startDestination.asStateFlow() //これが購読される

    // 画面初期化(onCreate)時にログイン状況を確認して遷移用の値を流します
    fun onCreate() {
        viewModelScope.launch {
            if (checkLoginService.execute()) {
                _startDestination.value = PublicTimelineDestination()
            } else {
                _startDestination.value = LoginDestination()
            }
        }
    }


}