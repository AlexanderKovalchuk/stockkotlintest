package com.kaval.android.myapplication.util

import android.content.Context
import android.util.Log
import com.kaval.android.myapplication.data.model.Content
import java.io.IOException
import java.io.InputStream

class JsonLoader {

    fun loadJsonDataFromAsset(inputStream: InputStream): String {
        val jsonString: String
        try {
            jsonString = inputStream.bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            Log.e(this.javaClass.toString(), "read json String from file failed", ioException)
            return "";
        }
        return jsonString
    }


}