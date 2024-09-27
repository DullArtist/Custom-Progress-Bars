package com.app.charts

data class CheckUp(
    var question: String? = null,
    var type: String? = null,
    var options: List<String>? = null,
    var isAnswered: Boolean = false
)
