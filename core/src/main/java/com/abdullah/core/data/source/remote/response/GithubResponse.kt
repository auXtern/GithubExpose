package com.abdullah.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GithubResponse(
	@field:SerializedName("items")
	val items: List<ItemsItem>? = null
)

data class ItemsItem(
	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int,
)
