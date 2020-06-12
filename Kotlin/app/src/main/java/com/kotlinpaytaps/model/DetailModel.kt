package com.kotlinpaytaps.model

import java.io.Serializable

class DetailModel : Serializable {
    var orderID: String = ""
    var transactionTitle: String = ""
    var amount: String = ""
    var email: String = ""
    var number: String = ""
    var transactionId: String = ""
    var token: String = ""
}
