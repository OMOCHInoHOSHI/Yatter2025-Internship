package com.dmm.bootcamp.yatter2025.ui.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    // {}内のコンポーザブルを横一列に並べる
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) , // 縦方向(vertical)に4dpのpaddingを当てる
        horizontalArrangement = Arrangement.spacedBy(8.dp), //横一列に並べるコンポーザブル同士の隙間を8dp空ける
    ){

    }

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