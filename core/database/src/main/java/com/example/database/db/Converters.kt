package com.example.database.db

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromDoubleArrayList(value: ArrayList<Double>): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun toDoubleArrayList(value: String): ArrayList<Double> {
        return ArrayList(value.split(",").map { it.toDouble() })
    }
}
