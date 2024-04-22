package com.example.composelesson.AccountScreen

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composelesson.R
import my.app.android.Mask


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountAlert(
    font_m_regular: FontFamily,
    font_m_light: FontFamily,
    font_m_semibold: FontFamily,
    showAlert: MutableState<Boolean>,
    user: MutableState<User>,
    phoneMask: Mask) {
    var newName by remember { mutableStateOf(user.value.name)}
    var newPhoneNumber by remember { mutableStateOf(user.value.phoneNumber)}
    AlertDialog(
        containerColor = colorResource(id = R.color.element_background),
        title = { Text(
            text = "Редактирование",
            color = colorResource(id = R.color.white),
            fontFamily = font_m_semibold) },
        text = {
            Column() {
                OutlinedTextField(
                    value = newName,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = colorResource(id = R.color.white),
                        unfocusedIndicatorColor = colorResource(id = R.color.white),
                        containerColor = colorResource(id = R.color.element_background),
                        cursorColor = colorResource(id = R.color.yellow)),
                    shape = RoundedCornerShape(15.dp),
                    textStyle = TextStyle(
                        color = colorResource(id = R.color.white),
                        fontFamily = font_m_regular,
                        fontSize = 15.sp

                    ),
                    onValueChange = {if (it.length <= 23) newName = it },
                    label = {
                        Text(
                            text = "Имя",
                            fontFamily = font_m_light,
                            fontSize = 10.sp,
                            color = colorResource(id = R.color.white)
                        )
                    },

                    )
                OutlinedTextField(
                    value = newPhoneNumber,
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
                    visualTransformation = phoneMask,
                    onValueChange = { if (it.length <= 10) newPhoneNumber = it },
                    label = { Text(
                        text = "Номер телефона",
                        fontFamily = font_m_light,
                        fontSize = 10.sp,
                        color = colorResource(id = R.color.white))},

                    )
            }
        },
        confirmButton =  { Button(
            content = { Icon(
                painter = painterResource(id = R.drawable.check),
                contentDescription = "",
                tint = colorResource(id = R.color.element_background),
                modifier = Modifier.size(18.dp)
            )},
            onClick = { showAlert.value = !showAlert.value
                        user.value.name = newName
                        user.value.phoneNumber = newPhoneNumber
                      },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.yellow))
            )},
        onDismissRequest = { showAlert.value = !showAlert.value  },
        dismissButton = { Button(
            content = { Icon(
                painter = painterResource(id = R.drawable.cross),
                contentDescription = "",
                tint = colorResource(id = R.color.element_background),
                modifier = Modifier.size(20.dp)
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.yellow)),
            onClick = { showAlert.value = !showAlert.value }
        )

        })
}


