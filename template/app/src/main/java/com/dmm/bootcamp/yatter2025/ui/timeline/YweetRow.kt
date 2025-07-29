package com.dmm.bootcamp.yatter2025.ui.timeline

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dmm.bootcamp.yatter2025.ui.theme.Yatter2025Theme
import com.dmm.bootcamp.yatter2025.ui.timeline.bindingmodel.ImageBindingModel
import com.dmm.bootcamp.yatter2025.ui.timeline.bindingmodel.YweetBindingModel

// YweetRowコンポーザブルを定義
// 1つのYweetを表示するコンポーザブル
// コンポーザブルを定義する時にはModifierも引数で受け取る
@Composable
fun YweetRow(
    yweetBindingModel: YweetBindingModel,
    modifier: Modifier = Modifier,
) {
}

@Preview
@Composable
private fun YweetRowPreview() {
    Yatter2025Theme {
        Surface {
            YweetRow(
                yweetBindingModel = YweetBindingModel(
                    id = "id",
                    displayName = "mito",
                    username = "mitohato14",
                    avatar = "https://avatars.githubusercontent.com/u/19385268?v=4",
                    content = "preview content",
                    attachmentImageList = listOf(
                        ImageBindingModel(
                            id = "id",
                            type = "image",
                            url = "https://avatars.githubusercontent.com/u/39693306?v=4",
                            description = "icon"
                        )
                    )
                )
            )
        }
    }
}