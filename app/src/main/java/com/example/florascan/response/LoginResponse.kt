package com.example.florascan.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("loginResult")
	val loginResult: LoginResult,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class LoginResult(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("token")
	val token: String
)
