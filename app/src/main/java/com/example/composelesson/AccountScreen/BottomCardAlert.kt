package com.example.composelesson.AccountScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composelesson.R
import my.app.android.Mask

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomCardAlert(
    font_m_regular: FontFamily,
    font_m_semibold: FontFamily,
    font_m_light: FontFamily,
    card: MutableState<Card>,
    showDialog: MutableState<Boolean>,
    cards: MutableState<List<Card>>,
    cardMask: Mask,
    dataMask: Mask
) {

    var newName by remember { mutableStateOf(card.value.number) }
    var newCVC by remember { mutableStateOf(card.value.cvc) }
    var newDate by remember { mutableStateOf(card.value.data) }

    ModalBottomSheet(
        containerColor = colorResource(id = R.color.element_background),
        onDismissRequest = { showDialog.value = !showDialog.value },
    ) {
        Column(
            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Данные карты",
                fontFamily = font_m_semibold,
                fontSize = 20.sp,
                color = colorResource(id = R.color.white),
                modifier = Modifier.padding(bottom = 10.dp)
            )

            OutlinedTextField(
                value = newName,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = cardMask,
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
                onValueChange = { if (it.length <= 16) newName = it },
                label = {
                    Text(
                        text = "Номер карты",
                        fontFamily = font_m_light,
                        fontSize = 10.sp,
                        color = colorResource(id = R.color.white)
                    )
                },
            )
            Row{
                OutlinedTextField(
                    value = newCVC,
                    modifier = Modifier.width(150.dp),
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
                    onValueChange = {if (it.length <= 3) newCVC = it },
                    label = {
                        Text(
                            text = "CVV/CVC-код",
                            fontFamily = font_m_light,
                            fontSize = 10.sp,
                            color = colorResource(id = R.color.white)
                        )
                    },
                )
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = newDate,
                    modifier = Modifier.width(150.dp),
                    visualTransformation = dataMask,
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
                    onValueChange = { if (it.length <= 4) newDate = it },
                    label = {
                        Text(
                            text = "MM/ГГ",
                            fontFamily = font_m_light,
                            fontSize = 10.sp,
                            color = colorResource(id = R.color.white)
                        )
                    },
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    showDialog.value = !showDialog.value
                    cards.value = cards.value.toMutableList().apply {add(Card(number = newName, cvc = newCVC, data = newDate))}
                          },
                content = {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.material.Text(
                            text = "Сохранить",
                            fontFamily = font_m_regular,
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.background)

                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.yellow)
                )
            )
        }

    }
}