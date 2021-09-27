package com.example.picsumtest.ui.Auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.picsumtest.Api
import com.example.picsumtest.R
import com.example.picsumtest.data.model.WeatherInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.jar.Attributes
import kotlin.coroutines.coroutineContext

suspend fun loginClick(appContext:Context){
    val api = Api()
    val weather = MutableStateFlow<WeatherInfo?>(null)
        weather.value = api.loadWeather()
        Log.d(
            "test", "${weather.value?.name}, ${weather.value?.main?.temp}, ${
                weather.value?.weather?.get(0)?.description
            }"
        )
        val welcome = "${weather.value?.name}, ${weather.value?.main?.temp}, ${
            weather.value?.weather?.get(0)?.description
        }"
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
}


@Preview
@Composable
fun AuthCompose() {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val isValidMail = user.count() > 5 && '@' in user && '.' in user && user.last() != '.'
    val isValidPass =
        pass.count() > 5 && pass.contains(Regex("[A-Z]")) && pass.contains(Regex("[0-9]"))
    Column(
        Modifier.padding(8.dp)
    ) {
        val logoImage: Painter = painterResource(id = R.drawable.logo)
        Image(
            logoImage,
            contentDescription = "",
            Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        )
        Column(
            /*modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top*/
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .background(
                        color = Color(0xffb9b9b9),
                        shape = RoundedCornerShape(percent = 10)
                    ),
                value = user,
                onValueChange = { txt -> user = txt },
                label = { Text(text = "Имя пользователя") }, isError = !isValidMail
            )
            val textColor =
                if (!isValidMail) MaterialTheme.colors.error else MaterialTheme.colors.onSurface
            if (isValidMail != true) {
                Text(
                    textAlign = TextAlign.Center,
                    text = if (!isValidMail) "Не менее 5 символов вместе с @" else "",
                    style = MaterialTheme.typography.caption.copy(color = textColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        Spacer(modifier = Modifier.width(4.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .background(
                    color = Color(0xffb9b9b9),
                    shape = RoundedCornerShape(percent = 10)
                ),
            value = pass,
            onValueChange = { txt -> pass = txt },
            label = { Text(text = "Пароль") },
            isError = !isValidPass,
            visualTransformation = PasswordVisualTransformation()
        )
        val textColor =
            if (!isValidPass) MaterialTheme.colors.error else MaterialTheme.colors.onSurface
        Text(
            textAlign = TextAlign.Center,
            text = if (!isValidPass) "Должен быть не менее 5 символов, содержать цифру и заглавную букву" else "",
            style = MaterialTheme.typography.caption.copy(color = textColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .align(Alignment.CenterHorizontally)
        )

        Button(
            onClick = {

                coroutineScope.launch {
                    loginClick(context)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Войти")

        }
    }

}