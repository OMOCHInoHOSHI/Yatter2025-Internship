package com.dmm.bootcamp.yatter2025.ui.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter2025.R
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

//        URLから取得した画像をアバター画像として表示しますが、URLから画像取得できなかった場合を考慮して、プレイスホルダーを設定
        //現在のコンポーザブルが関連付けられるコンテキストを取得   // currentは現在
        val context = LocalContext.current

        // プレイスホルダー画像の生成
        val placeholder = ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.avatar_placeholder,
            null,
        )


        // アバター画像はURLをもとに画像を取得して表示
        // 画像データのキャッシュ等を楽に管理するため、今回はCoilというライブラリが提供している、AsyncImageを利用
        AsyncImage(
            modifier = Modifier.size(48.dp),    // アイコン画像のサイズ
//            model = yweetBindingModel.avatar,   // 画像URLなどの画像
            // ImageRequestを作成して、画像取得できていない状態のプレイスホルダー設定
            model = ImageRequest.Builder(context)
                .data(yweetBindingModel.avatar)
                .placeholder(placeholder)
                .error(placeholder)
                .fallback(placeholder)
                .setHeader("User-Agent", "Mozilla/5.0") // モックサーバーから画像取得する場合のみ追加
                .build(),
//            contentDescription = "アバター画像",  // 画像の説明テキスト
            contentDescription = stringResource(id = R.string.public_timeline_avatar_content_description),
            contentScale = ContentScale.Crop,   // コンポーザブルのサイズにCrop(空いているスペースがなくなるように画像を切り抜いて中央に配置)
        )

        // 表示名とユーザー名、Yweetの内容を縦方向に並べるためにColumnを利用
        Column(
            // Columnでは縦方向になるため、verticalArrangementに余白
            verticalArrangement = Arrangement.spacedBy(4.dp))
        {
            // 横に並べるだけであれば、先ほども利用したRowが利用できそうですが、今回は表示名とユーザー名が画面からはみ出るほど長かった場合に、途中で文字列を切って、「...」で省略するために、1つのTextコンポーザブルで表現します。
            Text(
                text = buildAnnotatedString {
                    // appendで文字列セット
                    append(yweetBindingModel.displayName)
                    withStyle(
                        style = SpanStyle(
                            // 文字色を薄くするために、ContentAlpha.mediumを指定
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                        )
                    ) {
                        append(" @${yweetBindingModel.username}")
                    }
                },
                maxLines = 1, // 文字列が複数行にならないように指定
                overflow = TextOverflow.Ellipsis, // はみ出した分を「...」で表現
                fontWeight = FontWeight.Bold, // 文字を太字に
            )

        }
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