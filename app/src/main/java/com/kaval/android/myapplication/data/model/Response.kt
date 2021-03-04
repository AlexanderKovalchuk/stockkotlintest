package com.kaval.android.myapplication.data.model

import com.google.gson.annotations.SerializedName

class Response(var content: Content, val status: String) {

}

class Content( @SerializedName("quoteSymbols") var stocks: List<Stock>){
}