package ShoppingScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composelesson.MainViewModel
import com.example.composelesson.MenuScreen.Item
import com.example.composelesson.MenuScreen.Meel
import com.example.composelesson.R


@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingList(
    font_m_semibold: FontFamily,
    font_m_regular: FontFamily,
    font_m_light: FontFamily,
    viewModel: MainViewModel
) {
    val shoppingList = viewModel.shoppingList.collectAsState()
    var newComment by remember { mutableStateOf("") }
    val showPayingType = remember { mutableStateOf(false) }
    val showPicker = remember { mutableStateOf(false) }
    val timeFlag = remember { mutableStateOf(true) }
    val hourState = viewModel.orderHour.collectAsState()
    val payingTypeState = viewModel.payingType.collectAsState()
    val minuteState = viewModel.orderMinute.collectAsState()
    val orderSum = viewModel.orderSum.collectAsState()
    var orderStr = viewModel.orderStr.collectAsState()

    val timeButtonMod1: Modifier
    val timeButtonMod2: Modifier
    val timeTextCol1: Color
    val timeTextCol2: Color

    if (showPayingType.value)
        PayTypeAlert(
            font_m_semibold = font_m_semibold,
            font_m_regular = font_m_regular,
            showAlert = showPayingType,
            viewModel)

    if (showPicker.value)
        BottomTimePickerAlert(
            font_m_regular = font_m_regular,
            font_m_semibold = font_m_semibold,
            showPicker = showPicker,
            timeFlag = timeFlag,
            viewModel)

    if (timeFlag.value)
    {
        timeButtonMod1 = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = colorResource(id = R.color.yellow))
        timeButtonMod2 = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = colorResource(id = R.color.element_background))
        timeTextCol1 = colorResource(id = R.color.element_background)
        timeTextCol2 = colorResource(id = R.color.white)
    }
    else
    {
        timeButtonMod1 = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = colorResource(id = R.color.element_background))
        timeButtonMod2 = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = colorResource(id = R.color.yellow))
        timeTextCol1 = colorResource(id = R.color.white)
        timeTextCol2 = colorResource(id = R.color.element_background)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Заказ",
                fontFamily = font_m_semibold,
                fontSize = 20.sp,
                color = colorResource(id = R.color.white),
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "",
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(15.dp)
                    )
                    Text(
                        text = "Очистить корзину",
                        fontFamily = font_m_light,
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.white),
                        modifier = Modifier.clickable { viewModel.clearList() }
                    )
                }

            }

        }

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
        ) {
            LazyColumn(
            ) {
                itemsIndexed(shoppingList.value) { index, item ->
                    Item(
                        font_m_semibold = font_m_semibold,
                        font_m_regular = font_m_regular,
                        font_m_light = font_m_light,
                        viewModel = viewModel,
                        meal = item
                    )
                }

            }
        }

        Divider(modifier = Modifier.padding(top = 15.dp, bottom = 5.dp))

        OutlinedTextField(
            value = newComment,
            modifier = Modifier
                .fillMaxWidth(),
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
            onValueChange = { newComment = it },
            label = {
                Text(
                    text = "Комментарий к заказу",
                    fontFamily = font_m_light,
                    modifier = Modifier.padding(0.dp),
                    fontSize = 10.sp,
                    color = colorResource(id = R.color.white)
                )
            },
        )

        Divider(modifier = Modifier.padding(vertical = 15.dp))

        Text(
            text = "Доставка",
            fontFamily = font_m_semibold,
            fontSize = 20.sp,
            color = colorResource(id = R.color.white),
            modifier = Modifier.padding(bottom = 15.dp)
        )

        DeliveryBox(
            font_m_light = FontFamily.Default,
            font_m_semibold = FontFamily.Default,
            font_m_regular = FontFamily.Default
        )
        Divider(modifier = Modifier.padding(vertical = 15.dp))

        Row(
            modifier = Modifier.padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Заказ приготовим",
                fontFamily = font_m_light,
                fontSize = 15.sp,
                color = colorResource(id = R.color.white)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Сегодня до ${hourState.value}:${minuteState.value}",
                fontFamily = font_m_light,
                fontSize = 15.sp,
                color = colorResource(id = R.color.white)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = timeButtonMod1
                    .clickable {
                        timeFlag.value = true
                        viewModel.changeOrderTime(viewModel.orderHour.value,viewModel.orderMinute.value)
                    }
            ) {
                Text(
                    text = "Как можно скорее",
                    fontFamily = font_m_regular,
                    fontSize = 15.sp,
                    color = timeTextCol1,
                    modifier = Modifier.padding(5.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = timeButtonMod2
                    .clip(shape = RoundedCornerShape(20.dp))
                    .clickable {
                        timeFlag.value = false
                        showPicker.value = !showPicker.value
                    }
            ) {
                Text(
                    text = "Ко времени",
                    fontFamily = font_m_regular,
                    fontSize = 15.sp,
                    color = timeTextCol2,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }

        Divider(modifier = Modifier.padding(vertical = 15.dp))

        Row(
            modifier = Modifier.padding(bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Спопоб оплаты",
                fontFamily = font_m_regular,
                fontSize = 15.sp,
                color = colorResource(id = R.color.white)
            )
            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(color = colorResource(id = R.color.element_background))
                    .clickable { showPayingType.value = !showPayingType.value }
            ) {
                Text(
                    text = payingTypeState.value,
                    fontFamily = font_m_regular,
                    fontSize = 15.sp,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
        if (viewModel.showButtonPayment.value)
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.yellow)
                ),
                onClick = {},
                content = {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = orderStr.value,
                            fontFamily = font_m_regular,
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.background)

                        )
                    }
                }
            )

    }
}
