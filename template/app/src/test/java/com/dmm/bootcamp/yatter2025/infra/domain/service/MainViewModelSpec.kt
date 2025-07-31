package com.dmm.bootcamp.yatter2025.infra.domain.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dmm.bootcamp.yatter2025.common.MainDispatcherRule
import com.dmm.bootcamp.yatter2025.domain.service.CheckLoginService
import com.dmm.bootcamp.yatter2025.ui.MainViewModel
import com.dmm.bootcamp.yatter2025.ui.login.LoginDestination
import com.dmm.bootcamp.yatter2025.ui.timeline.PublicTimelineDestination
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.rules.TestRule
import kotlin.test.Test


// MainViewModelのテスト実装例
class MainViewModelSpec {
    private val checkLoginService = mockk<CheckLoginService>()
    private val subject = MainViewModel(checkLoginService)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun navigateToPublicTimelineWhenLoggedIn() = runTest {
        coEvery {
            checkLoginService.execute()
        } returns true

        subject.onCreate()

        assertThat(subject.startDestination.value).isInstanceOf(PublicTimelineDestination::class.java)
    }

    @Test
    fun navigateToLoginWhenNotLoggedIn() = runTest {
        coEvery {
            checkLoginService.execute()
        } returns false

        subject.onCreate()

        assertThat(subject.startDestination.value).isInstanceOf(LoginDestination::class.java)
    }
}