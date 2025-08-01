package com.dmm.bootcamp.yatter2025.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter2025.R
import com.dmm.bootcamp.yatter2025.domain.model.Password
import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.ui.theme.Yatter2025Theme

@Composable
fun NewuserTemplate(
    userName: String,
    onChangedUserName: (String) -> Unit,
    password: String,
    onChangedPassword: (String) -> Unit,
    isEnableLogin: Boolean,
    isLoading: Boolean,
//    onClickLogin: () -> Unit,
    onClickRegister: () -> Unit,
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.news) + stringResource(R.string.Registration))
                }
            )
        }

    ) {
        paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp),

//            contentAlignment = Alignment.Center

        ){
            // 縦に並べる
            Column(
                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.Center
            ) {


                // ユーザー名

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = stringResource(R.string.username)
                )

                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    value = userName,
                    onValueChange = onChangedUserName,
                    placeholder = {
                        Text(text = stringResource(R.string.username_eng))
                    }
                )

                // パスワード

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.password)
                )

                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    value = password,
                    onValueChange = onChangedPassword,
                    placeholder = {
                        Text(text = stringResource(R.string.password_eng))
                    }
                )


                // 新規登録
                Button(
                    enabled = isEnableLogin,
                    onClick = onClickRegister,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(text = stringResource(R.string.news) + stringResource(R.string.Registration))
                }


            }


            // loading
            if (isLoading) {
                // 確定インジケーター デフォルト
                CircularProgressIndicator()
            }

        }
    }
}


@Preview
@Composable
fun NewuserTemplatePreview() {
    Yatter2025Theme {
        Surface {
            com.dmm.bootcamp.yatter2025.ui.signup.NewuserTemplate (
                userName = "username",
                onChangedUserName = {},
                password = "password",
                onChangedPassword = {},
                isEnableLogin = true,
                isLoading = false,
//                onClickLogin = {},
                onClickRegister = {},
            )
        }
    }
}