package com.example.iswara.data.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CeritaResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("listCerita")
	val listCerita: List<Cerita>?

)

@Parcelize
data class Cerita(

	@field:SerializedName("id")
	val idCerita: String,

	@field:SerializedName("name")
	var name: String,

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("cerita")
	val cerita: String,

	@field:SerializedName("tanggapan_count")
	val tanggapanCount: Int,

	@field:SerializedName("support_count")
	val supportCount: Int

) : Parcelable


data class TanggapanResponse (

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("listTanggapan")
	val listTanggapan: List<Tanggapan>?
)

@Parcelize
data class Tanggapan (

	@field:SerializedName("id")
	val idTanggapan: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("tanggapan")
	val tanggapan: String,
) : Parcelable

@Parcelize
data class User (

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("phoneNum")
	val phoneNum: String?

) : Parcelable

// -----------------------------------------------

data class LoginResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("loginResult")
	val loginResult: LoginResult? = null
)

data class LoginResult(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("token")
	val token: String
)

data class StoryResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("listStory")
	val listStory: List<StoryItem>? = null,

	)

@Parcelize
data class StoryItem(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("lat")
	val lat: Double? = null,

	@field:SerializedName("lon")
	val lon: Double? = null

) : Parcelable

data class FileUploadResponse(
	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)