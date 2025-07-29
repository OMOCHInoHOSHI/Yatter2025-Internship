package com.dmm.bootcamp.yatter2025.ui.timeline

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter2025.ui.theme.Yatter2025Theme
import com.dmm.bootcamp.yatter2025.ui.timeline.bindingmodel.YweetBindingModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PublicTimelineTemplate(
    yweetList: List<YweetBindingModel>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
) {

    // リフレッシュにはPullRefreshStateという状態オブジェクトが必要
    // PullToRefreshが実行されたときに、onRefreshの処理を発火
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh)

    // BoxコンポーザブルでLazyColumnをラップ
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        LazyColumn (
            // 画面全体に広がったままでは視認性が悪いため、contentPaddingを指定しLazyColumn内の要素にpaddingがつくようにしてみます
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
        ){
            items(yweetList){ item ->
                YweetRow(yweetBindingModel = item)
            }
        }
    }


}


@Preview
@Composable
private fun PublicTimelineTemplatePreview() {
    Yatter2025Theme {
        Surface {
            PublicTimelineTemplate(
                yweetList = listOf(
                    YweetBindingModel(
                        id = "id1",
                        displayName = "display name1",
                        username = "username1",
                        avatar = null,
                        content = "preview content1",
                        attachmentImageList = listOf()
                    ),
                    YweetBindingModel(
                        id = "id2",
                        displayName = "display name2",
                        username = "username2",
                        avatar = null,
                        content = "preview content2",
                        attachmentImageList = listOf()
                    ),
                ),
                isLoading = true,
                isRefreshing = false,
                onRefresh = {}
            )
        }
    }
}