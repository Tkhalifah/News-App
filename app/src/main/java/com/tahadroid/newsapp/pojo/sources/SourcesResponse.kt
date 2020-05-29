package com.tahadroid.newsapp.pojo.sources

import com.google.gson.annotations.SerializedName

data class SourcesResponse(

	@field:SerializedName("sources")
	val sources: List<SourcesItem>,

	@field:SerializedName("status")
	val status: String
)