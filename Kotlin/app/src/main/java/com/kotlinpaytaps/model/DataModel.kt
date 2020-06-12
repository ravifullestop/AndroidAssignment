package com.kotlinpaytaps.model

class DataModel(title: String, value: String) {
    var title: String
        internal set
    var value: String
        internal set

    init {
        this.title = title
        this.value = value
    }

}
