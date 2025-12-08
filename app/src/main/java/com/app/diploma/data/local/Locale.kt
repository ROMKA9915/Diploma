package com.app.diploma.data.local

enum class Locale(
    val tag: String,
) {

    Ru("ru"),
    En("en"),

    ;

    companion object {

        fun retrieveByName(name: String?) = entries.find { it.name == name }
        fun retrieveByLanguageTag(tag: String?) = entries.find { it.tag == tag }
    }

}
