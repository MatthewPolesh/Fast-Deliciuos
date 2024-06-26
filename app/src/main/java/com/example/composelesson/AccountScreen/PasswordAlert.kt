package com.example.composelesson.AccountScreen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composelesson.MainViewModel
import com.example.composelesson.R

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordAlert(
    font_m_regular: FontFamily,
    font_m_light: FontFamily,
    font_m_semibold: FontFamily,
    showAlert: MutableState<Boolean>,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    var Mail by remember { mutableStateOf("") }
    AlertDialog(
        containerColor = colorResource(id = R.color.element_background),
        title = {
            Text(
                text = "Смена пароля",
                color = colorResource(id = R.color.white),
                fontFamily = font_m_semibold
            )
        },
        text = {
            Column() {
                OutlinedTextField(
                    value = Mail,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = colorResource(id = R.color.white),
                        unfocusedIndicatorColor = colorResource(id = R.color.white),
                        containerColor = colorResource(id = R.color.element_background),
                        cursorColor = colorResource(id = R.color.yellow)
                    ),
                    shape = RoundedCornerShape(15.dp),
                    textStyle = TextStyle(
                        color = colorResource(id = R.color.white),
                        fontFamily = font_m_regular,
                        fontSize = 15.sp
                    ),
                    onValueChange = { if (it.length <= 30) Mail = it },
                    label = {
                        Text(
                            text = "Введите почту",
                            fontFamily = font_m_light,
                            fontSize = 10.sp,
                            color = colorResource(id = R.color.white)
                        )
                    },
                )
            }
        },
        confirmButton = {
            Button(
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.check),
                        contentDescription = "",
                        tint = colorResource(id = R.color.element_background),
                        modifier = Modifier.size(18.dp)
                    )
                },
                onClick = {
                    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
                    when{
                        Mail.isEmpty() -> Toast.makeText(context, "Пожалуйста, введите адрес электронной почты", Toast.LENGTH_SHORT).show()
                        !emailRegex.matches(Mail) -> Toast.makeText(context,"Неверный адрес электронной почты",Toast.LENGTH_SHORT).show()
                        else ->{
                            showAlert.value = !showAlert.value
                            viewModel.resetUserPassword(Mail, context){
                                    success, message ->
                                if (success)
                                    Toast.makeText(context, "Вам отправили письмо для смены пароля", Toast.LENGTH_SHORT).show()
                                else
                                    Toast.makeText(context, "Ошибка смены пароля", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.yellow))
            )
        },
        onDismissRequest = { showAlert.value = !showAlert.value },
        dismissButton = {
            Button(
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.cross),
                        contentDescription = "",
                        tint = colorResource(id = R.color.element_background),
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier.padding(end = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.yellow)),
                onClick = { showAlert.value = !showAlert.value }
            )


        })
}

