package com.dmm.bootcamp.yatter2025.ui.post

import com.dmm.bootcamp.yatter2025.ui.post.bindingmodel.PostBindingModel


data class PostUiState(
    val bindingModel: PostBindingModel, // ツイートの投稿内容
    val isLoading: Boolean, // 読み込み中
) {
    companion object {
        fun empty(): PostUiState = PostUiState(
            bindingModel = PostBindingModel(
                avatarUrl = null,
                yweetText = ""
            ),
            isLoading = false,
        )
    }

    // ツイートの投稿ができるかどうか
    // PostBindingModel#yweetTextが空かどうかで
    val canPost: Boolean
        get() = bindingModel.yweetText.isNotBlank()
}