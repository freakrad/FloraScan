package com.example.florascan.helper

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("predictions")
	val predictions: List<PredictionsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class PredictionsItem(

	@field:SerializedName("mime_type")
	val mimeType: String? = null,

	@field:SerializedName("prediction_data")
	val predictionData: String? = null,

	@field:SerializedName("file_name")
	val fileName: String? = null,

	@field:SerializedName("prediction_score")
	val predictionScore: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("image_encoded")
	val imageEncoded: String? = null,

	@field:SerializedName("saved_url")
	val urlImage: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)
