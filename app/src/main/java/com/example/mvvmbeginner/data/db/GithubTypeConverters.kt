package com.example.mvvmbeginner.data.db

import androidx.room.TypeConverter
import timber.log.Timber
import java.lang.NumberFormatException
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

class GithubTypeConverters {

    companion object {
        fun splitToIntList(input: String?): List<Int>? {
            if (input == null) return null

            val result = ArrayList<Int>()
            val tokenizer = StringTokenizer(input, ",")

            while (tokenizer.hasMoreElements()) {
                val item = tokenizer.nextToken()

                try {
                    result.add(item.toInt())
                } catch (ex: NumberFormatException) {
                    Timber.e(ex)
                }
            }

            return result
        }

        fun joinIntToString(input: List<Int>?): String? {
            if (input == null) return null

            val size = input.size
            if (size == 0) return ""

            val sb = StringBuilder()
            for (i in 0 until size) {
                sb.append(input[i].toString())
                if (i < size - 1) sb.append(",")
            }

            return sb.toString()
        }
    }

    @TypeConverter
    fun stringToIntList(data: String?): List<Int>? {
        if (data == null) {
            return Collections.emptyList()
        }

        return GithubTypeConverters.splitToIntList(data)
    }

    @TypeConverter
    fun intListToString(numbers: List<Int>?): String? {
        return GithubTypeConverters.joinIntToString(numbers)
    }
}