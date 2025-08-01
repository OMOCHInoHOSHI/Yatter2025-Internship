package com.dmm.bootcamp.yatter2025.infra.domain.repository

import android.accounts.AuthenticatorException
import com.dmm.bootcamp.yatter2025.auth.TokenProvider
import com.dmm.bootcamp.yatter2025.auth.TokenProviderImpl
import com.dmm.bootcamp.yatter2025.domain.model.User
import com.dmm.bootcamp.yatter2025.domain.model.UserId
import com.dmm.bootcamp.yatter2025.domain.model.Yweet
import com.dmm.bootcamp.yatter2025.domain.model.YweetId
import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.infra.api.YatterApi
import com.dmm.bootcamp.yatter2025.infra.api.json.UserJson
import com.dmm.bootcamp.yatter2025.infra.api.json.PostYweetJson
import com.dmm.bootcamp.yatter2025.infra.api.json.YweetJson
import com.dmm.bootcamp.yatter2025.infra.domain.converter.YweetConverter
import com.dmm.bootcamp.yatter2025.infra.pref.LoginUserPreferences
import com.dmm.bootcamp.yatter2025.infra.pref.TokenPreferences
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyAll
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.URL

class YweetRepositoryImplSpec {
  private val yatterApi = mockk<YatterApi>()
  private val tokenPreferences = mockk<TokenPreferences>()
  private val tokenProvider: TokenProvider = TokenProviderImpl(tokenPreferences)
  private val subject = YweetRepositoryImpl(yatterApi, tokenProvider)

  @Test
  fun getPublicTimelineFromApi() = runTest {
    val jsonList = listOf(
      YweetJson(
        id = "id",
        user = UserJson(
          id = "id",
          username = "username",
          displayName = "display name",
          note = "note",
          avatar = "https://www.google.com",
          header = "https://www.google.com",
          followingCount = 100,
          followersCount = 200,
          createdAt = "2023-06-02T12:44:35.030Z"
        ),
        content = "content",
        createdAt = "2023-06-02T12:44:35.030Z",
        attachmentImageList = emptyList(),
      )
    )

    val expect = listOf(
      Yweet(
        id = YweetId(value = "id"),
        user = User(
          id = UserId("id"),
          username = Username("username"),
          displayName = "display name",
          note = "note",
          avatar = URL("https://www.google.com"),
          header = URL("https://www.google.com"),
          followingCount = 100,
          followerCount = 200,
        ),
        content = "content",
        attachmentImageList = emptyList()
      )
    )

    coEvery {
      yatterApi.getPublicTimeline()
    } returns jsonList

    val result: List<Yweet> = subject.findAllPublic()

    coVerify {
      yatterApi.getPublicTimeline()
    }

    assertThat(result).isEqualTo(expect)
  }

  @Test
  fun postYweetWhenLoggedIn() = runTest {
    val loginUsername = "token"
    val content = "content"
    val token = "username $loginUsername"

    val yweetJson = YweetJson(
      id = "id",
      user = UserJson(
        id = "id",
        username = loginUsername,
        displayName = "",
        note = null,
        avatar = "https://www.google.com",
        header = "https://www.google.com",
        followingCount = 0,
        followersCount = 0,
        createdAt = ""
      ),
      content = content,
      createdAt = "",
      attachmentImageList = emptyList(),
    )

    coEvery {
      tokenPreferences.getAccessToken()
    } returns loginUsername

    coEvery {
      yatterApi.postYweet(any(), any())
    } returns yweetJson

    val expect = YweetConverter.convertToDomainModel(yweetJson)

    val result = subject.create(
      content,
      emptyList()
    )

    assertThat(result).isEqualTo(expect)

    coVerifyAll {
      tokenPreferences.getAccessToken()
      yatterApi.postYweet(
        token,
        PostYweetJson(
          yweet = content,
          imageList = emptyList()
        )
      )
    }
  }

  @Test
  fun postYweetWhenNotLoggedIn() = runTest {
    val content = "content"

    coEvery {
      tokenProvider.provide()
    } throws AuthenticatorException()


    var error: Throwable? = null
    var result: Yweet? = null

    try {
      result = subject.create(
        content,
        emptyList()
      )
    } catch (e: Exception) {
      error = e
    }


    assertThat(result).isNull()
    assertThat(error).isInstanceOf(AuthenticatorException::class.java)

    coVerify {
      tokenProvider.provide()
    }
  }
}
