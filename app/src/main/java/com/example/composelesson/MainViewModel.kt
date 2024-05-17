package com.example.composelesson

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composelesson.AccountScreen.Card
import com.example.composelesson.MenuScreen.Adress
import com.example.composelesson.MenuScreen.FoodType
import com.example.composelesson.MenuScreen.Meel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel : ViewModel() {

    //Database

    private val repository: FireBaseAccount = FireBaseAccount()


    //AccountMenu
    //AccountMenu--flags
    private val _registrationFlag: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val registrationFlag = _registrationFlag.asStateFlow()

    private val _registrationError = MutableStateFlow<String?>(null)
    val registrationError = _registrationError.asStateFlow()

    private val _entranceFlag: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val entranceFlag = _entranceFlag

    //AccountMenu--info
    private val _userName: MutableStateFlow<String> = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    private val _userPhone: MutableStateFlow<String> = MutableStateFlow("")
    val userPhone = _userPhone.asStateFlow()

    private val _userPassword: MutableStateFlow<String> = MutableStateFlow("")
    val userPassword = _userPassword.asStateFlow()

    private val _cards: MutableStateFlow<List<Card>> = MutableStateFlow<List<Card>>(emptyList())
    val cards = _cards.asStateFlow()
    //AccountMenu--fun

    fun deleteCard(card: Card) {
        val tempArr = cards.value.toMutableList()
        tempArr.removeIf { it.number == card.number }
        _cards.value = tempArr
    }

    fun addCard(card: Card) {
        _cards.value += card

    }

    fun changeUserInfo(userName: String, userPhone: String) {
        _userName.value = userName
        _userPhone.value = userPhone
    }

    @SuppressLint("SuspiciousIndentation")
    fun signInUser(email: String, password: String) {

        viewModelScope.launch {
            val result = repository.signInUser(email, password)
            if (result.isSuccess)
            {
                _entranceFlag.value = true
                val userdoc = repository.firestore.collection("users").document(repository.auth.currentUser!!.uid)
                userdoc.get().addOnSuccessListener { document ->
                    if (document != null)
                    {
                        Log.d("Mylog", "$document")
                        _userName.value = document.getString("name").toString()
                        _userPhone.value = document.getString("phone").toString()
                    }
                }
            }
            else
                _entranceFlag.value = false
        }
    }

    fun registerUser(email: String, password: String, name: String, phone: String) {
        viewModelScope.launch {
            val registrationResult = repository.registerUser(email, password, name, phone)
            Log.d("MyLog","$registrationResult")
            if (registrationResult.isSuccess) {
                val uid = registrationResult.getOrNull()
                if (uid != null) {
                    val userInfo = mapOf(
                        "name" to name,
                        "email" to email,
                        "phone" to phone
                    )
                    val firestoreResult = repository.addUserToFirestore(uid, userInfo)
                    if (firestoreResult.isSuccess) {
                        _registrationFlag.value = true
                        _userName.value = name
                        _userPhone.value = phone
                    } else {
                        _registrationError.value = firestoreResult.exceptionOrNull()?.message
                    }
                } else {
                    _registrationError.value = "UID is null"
                }
            } else {
                _registrationError.value = registrationResult.exceptionOrNull()?.message
            }
        }
    }

    fun Exit() {
        viewModelScope.launch { repository.singOut() }
        _userName.value = ""
        _userPhone.value = ""
        _entranceFlag.value = false
        _registrationFlag.value = false
        Log.d("MyLog", "${_entranceFlag.value}, ${_registrationFlag.value}")
    }

    //MainMenu
    //MainMenu--flags
    private val _showPrice: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var showPrice = _showPrice.asStateFlow()
    //MainMenu--info


    private val _userAdress: MutableStateFlow<Adress> = MutableStateFlow(Adress("", "", ""))
    val userAdress = _userAdress.asStateFlow()


    private val Meel1 = Meel("Пицца1", "Описание", 999, FoodType.PIZZA, 0, false)
    private val Meel2 = Meel("Пицца2", "Описание", 999, FoodType.PIZZA, 0)
    private val Meel3 = Meel("Пицца3", "Описание", 999, FoodType.PIZZA, 0)
    private val Meel4 = Meel("Пицца4", "Описание", 999, FoodType.PIZZA, 0)
    private val Meel5 = Meel("Пицца5", "Описание", 999, FoodType.PIZZA, 0)
    private val Meel6 = Meel("Бургер1", "Описание", 999, FoodType.BURGERS, 0)
    private val Meel7 = Meel("Бургер2", "Описание", 999, FoodType.BURGERS, 0)
    private val Meel8 = Meel("Бургер3", "Описание", 999, FoodType.BURGERS, 0)
    private val Meel9 = Meel("Бургер4", "Описание", 999, FoodType.BURGERS)
    private val Meel10 = Meel("Бургер5", "Описание", 999, FoodType.BURGERS)
    private val Meel11 = Meel("Паста1", "Описание", 999, FoodType.PASTA)
    private val Meel12 = Meel("Паста2", "Описание", 999, FoodType.PASTA)
    private val Meel13 = Meel("Паста3", "Описание", 999, FoodType.PASTA)
    private val Meel14 = Meel("Паста4", "Описание", 999, FoodType.PASTA)
    private val Meel15 = Meel("Паста5", "Описание", 999, FoodType.PASTA)
    private val Meel16 = Meel("Вок1", "Описание", 999, FoodType.WOK)
    private val Meel17 = Meel("Вок2", "Описание", 999, FoodType.WOK)
    private val Meel18 = Meel("Вок3", "Описание", 999, FoodType.WOK)
    private val Meel19 = Meel("Вок4", "Описание", 999, FoodType.WOK)
    private val Meel20 = Meel("Вок5", "Описание", 999, FoodType.WOK)
    private val Meel21 = Meel("Салат1", "Описание", 999, FoodType.SALADS)
    private val Meel22 = Meel("Салат2", "Описание", 999, FoodType.SALADS)
    private val Meel23 = Meel("Салат3", "Описание", 999, FoodType.SALADS)
    private val Meel24 = Meel("Салат4", "Описание", 999, FoodType.SALADS)
    private val Meel25 = Meel("Салат5", "Описание", 999, FoodType.SALADS)
    private val Meel26 = Meel("Напиток1", "Описание", 999, FoodType.DRINKS)
    private val Meel27 = Meel("Напиток2", "Описание", 999, FoodType.DRINKS)
    private val Meel28 = Meel("Напиток3", "Описание", 999, FoodType.DRINKS)
    private val Meel29 = Meel("Напиток4", "Описание", 999, FoodType.DRINKS)
    private val Meel30 = Meel("Напиток5", "Описание", 999, FoodType.DRINKS)
    private val tools = Meel("Приборы", "Вилка, салфетки", 0, FoodType.TOOLS, 0)
    private var _mealMenu: MutableStateFlow<List<Meel>> = MutableStateFlow<List<Meel>>(
        listOf(
            Meel1, Meel2, Meel3, Meel4,
            Meel5, Meel6, Meel7, Meel8,
            Meel9, Meel10, Meel11, Meel12,
            Meel13, Meel14, Meel15, Meel16,
            Meel17, Meel18, Meel19, Meel20,
            Meel21, Meel22, Meel23, Meel24,
            Meel25, Meel26, Meel27, Meel28,
            Meel29, Meel30
        )
    )
    var mealMenu = _mealMenu.asStateFlow()

    private val _selectedAdress: MutableStateFlow<Adress> = MutableStateFlow(Adress("", "", ""))
    val selectedAdress = _selectedAdress


    //MainMenu--fun

    fun changeAdress(adress: String, house: String, flat: String) {
        _selectedAdress.value = _selectedAdress.value.copy(
            adress = adress.trim(),
            house = house.trim(),
            flat = flat.trim()
        )
    }

    fun increaseCounter(name: String) {
        if (name != "Приборы") {
            val tempMenu = mealMenu.value.toMutableList()
            val tempShop = shoppingList.value.toMutableList()
            val tempMenuIndex = tempMenu.indexOfFirst { it.name == name }
            val tempShopIndex = tempShop.indexOfFirst { it.name == name }

            if (tempMenu[tempMenuIndex].counter == 0) {
                tempMenu[tempMenuIndex] = tempMenu[tempMenuIndex].copy(
                    counter = tempMenu[tempMenuIndex].counter + 1,
                    showPrice = true
                )
                if (tempShopIndex == -1)
                    tempShop += tempMenu[tempMenuIndex]
            } else {
                tempMenu[tempMenuIndex] =
                    tempMenu[tempMenuIndex].copy(counter = tempMenu[tempMenuIndex].counter + 1)
                tempShop[tempShopIndex] =
                    tempShop[tempShopIndex].copy(counter = tempShop[tempShopIndex].counter + 1)
            }
            _shoppingList.value = tempShop
            _mealMenu.value = tempMenu
            countOrderSum()
        }
    }

    fun decreaseCounter(name: String) {
        val tempMenu = mealMenu.value.toMutableList()
        val tempShop = shoppingList.value.toMutableList()
        val tempMenuIndex = tempMenu.indexOfFirst { it.name == name }
        val tempShopIndex = tempShop.indexOfFirst { it.name == name }


        if (tempMenu[tempMenuIndex].counter == 1) {
            tempMenu[tempMenuIndex] = tempMenu[tempMenuIndex].copy(counter = 0, showPrice = false)
            tempShop.removeIf { it.name == name }
        } else {
            tempMenu[tempMenuIndex] =
                tempMenu[tempMenuIndex].copy(counter = tempMenu[tempMenuIndex].counter - 1)
            tempShop[tempShopIndex] =
                tempShop[tempShopIndex].copy(counter = tempShop[tempShopIndex].counter - 1)
        }
        _shoppingList.value = tempShop
        _mealMenu.value = tempMenu
        countOrderSum()
    }


    //ShoppingList
    //ShoppingList--flags
    private val _showButtonPayment: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showButtonPayment = _showButtonPayment.asStateFlow()

    private val _orderTimeFlag: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val orderTimeFlag = _orderTimeFlag.asStateFlow()

    //ShoppingList--info
    private val _orderStr: MutableStateFlow<String> = MutableStateFlow("")
    val orderStr = _orderStr.asStateFlow()

    private val _orderSum: MutableStateFlow<Int> = MutableStateFlow(0)
    val orderSum = _orderSum.asStateFlow()

    private val _currentHour: MutableStateFlow<String> =
        MutableStateFlow(LocalTime.now().hour.toString())
    val currentHour = _currentHour.asStateFlow()

    private val _currentMinute: MutableStateFlow<String> =
        MutableStateFlow(LocalTime.now().minute.toString())
    val currentMinute = _currentMinute.asStateFlow()

    private val _orderHour: MutableStateFlow<String> =
        MutableStateFlow((_currentHour.value.toInt() + 1).toString())
    val orderHour = _orderHour.asStateFlow()

    private val _orderMinute: MutableStateFlow<String> =
        MutableStateFlow((_currentMinute.value.substring(0, 1) + '5'))
    val orderMinute = _orderMinute.asStateFlow()

    private val _selectedHour: MutableStateFlow<String> = MutableStateFlow(orderHour.value)
    val selectedHour = _selectedHour

    private val _selectedMinute: MutableStateFlow<String> = MutableStateFlow(orderMinute.value)
    val selectedMinute = _selectedMinute

    private val _payingType: MutableStateFlow<String> = MutableStateFlow("Картой")
    val payingType = _payingType.asStateFlow()

    private val _shoppingList: MutableStateFlow<List<Meel>> =
        MutableStateFlow<List<Meel>>(listOf(tools))
    val shoppingList = _shoppingList.asStateFlow()

    private val _orderComment: MutableStateFlow<String> = MutableStateFlow("")
    val orderComment = _orderComment


    //ShoppingList--fun
    fun changeOrderTimeFlag() {
        _orderTimeFlag.value = !_orderTimeFlag.value
    }

    fun changeOrderComment(newComment: String) {
        _orderComment.value = newComment
    }

    fun countOrderSum() {
        if (_shoppingList.value.size == 1) {
            _showButtonPayment.value = false
            createOrderSum(0)
        } else {
            _showButtonPayment.value = true
            createOrderSum(_shoppingList.value.sumBy { it.price * it.counter })
        }
    }

    fun createOrderSum(sum: Int) {
        _orderStr.value = "Оплатить $sum₽"

    }

    fun clearList() {
        val temp = ArrayList<Meel>()
        temp.add(tools)
        _shoppingList.value = temp
        _mealMenu.value.forEach { it.counter = 0 }
        _mealMenu.value.forEach { it.showPrice = false }
    }

    fun changePayingType(payingType: String) {
        _payingType.value = payingType
    }

    fun changeOrderTime(hour: String, minute: String) {
        _selectedHour.value = hour
        _selectedMinute.value = minute
    }
}