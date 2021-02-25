package com.example.contributors

import com.eclipsesource.json.JsonObject

class Contributor(private val obj : JsonObject) {

    val _elements = mutableMapOf<String, String>()

    init {
        for (el in ELEMENTS.LIST) {
            _elements += mapOf(el to obj.get(el).toString().replace("\"", ""))
        }
    }

    fun element(key : String) = _elements[key]

}