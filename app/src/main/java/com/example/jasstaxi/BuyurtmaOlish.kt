package com.example.jasstaxi

class BuyurtmaOlish {
    lateinit var numberPhone: String
    lateinit var zakazAdress: String

    constructor()
    constructor(phoneNumber: String?, buyurtmaManzil: String?) {
        this.numberPhone = phoneNumber!!
        this.zakazAdress = buyurtmaManzil!!
    }

    override fun toString(): String {
        return "BuyurtmaOlish(numberPhone=$numberPhone, zakazAdress=$zakazAdress)"
    }

}