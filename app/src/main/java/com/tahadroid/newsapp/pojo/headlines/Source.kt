package com.tahadroid.newsapp.pojo.headlines

import com.google.gson.annotations.SerializedName

data class Source(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String
)