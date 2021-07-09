package com.example.jasstaxi.models

class BuyurtmaOlish {
    var numberPhone: String? = null
    var zakazAdress: String? = null

    constructor()
    constructor(phoneNumber: String?, buyurtmaManzil: String?)

    override fun toString(): String {
        return "BuyurtmaOlish(numberPhone=$numberPhone, zakazAdress=$zakazAdress)"
    }
    fun getHello(zakazAdress: String): String {
        return zakazAdress
    }
    fun getHello1(numberPhone: String): String {
        return numberPhone
    }

}