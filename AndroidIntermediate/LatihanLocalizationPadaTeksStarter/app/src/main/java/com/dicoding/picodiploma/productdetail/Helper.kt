package com.dicoding.picodiploma.productdetail

import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.withNumberingFormat(): String = NumberFormat.getNumberInstance().format(toDouble())
fun String.withDateFormat(): String {
    // Locale format akan menyesuaikan dengen user preferences walaupun pada constructor di set Locale.US, Locale.CHINA, dan lainnya
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    val date = format.parse(this) as Date
    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}

fun String.withCurrencyFormat(): String {
    val rupiahExchangeRate = 12495.95
    val euroExchangeRate = 0.88

    var priceOnDollar = toDouble() / rupiahExchangeRate
    var mCurrencyFormat = NumberFormat.getCurrencyInstance()
    val deviceLocale = Locale.getDefault().country
    when {
        deviceLocale.equals("ES") -> {
            // nilai euro terhadap dollar
            priceOnDollar *= euroExchangeRate
        }
        deviceLocale.equals("ID") -> {
            // nilai rupiah terhadap dollar
            priceOnDollar *= rupiahExchangeRate
        }
        else -> {
            // set default ke US
            mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
        }
    }
    val localPrice = priceOnDollar
    return mCurrencyFormat.format(localPrice)
}