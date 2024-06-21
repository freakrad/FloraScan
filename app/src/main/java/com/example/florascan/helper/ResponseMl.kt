package com.example.florascan.helper

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseMl(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class Result(

	@field:SerializedName("score")
	val score: Double? = null,

	@field:SerializedName("suggestion")
	val suggestion: Suggestion? = null,

	@field:SerializedName("prediction")
	val prediction: String? = null
) : Parcelable

@Parcelize
data class Suggestion(

	@field:SerializedName("Pengaturan Waktu dan Jarak Tanam")
	val pengaturanWaktuDanJarakTanam: List<String?>? = null,

	@field:SerializedName("Perawatan Daun dan Penyemprotan Fungisida")
	val perawatanDaunDanPenyemprotanFungisida: List<String?>? = null,

	@field:SerializedName("Penggunaan Varietas Tahan Penyakit")
	val penggunaanVarietasTahanPenyakit: List<String?>? = null,

	@field:SerializedName("Perawatan Benih")
	val perawatanBenih: List<String?>? = null,

	@field:SerializedName("Aplikasi Agen Pelapis Organik")
	val aplikasiAgenPelapisOrganik: List<String?>? = null,

	@field:SerializedName("Pendekatan Terpadu")
	val pendekatanTerpadu: List<String?>? = null,

	@field:SerializedName("Pengendalian Hayati")
	val pengendalianHayati: List<String?>? = null,

	@field:SerializedName("Penggunaan Fungisida")
	val penggunaanFungisida: List<String?>? = null,

	@field:SerializedName("Sanitasi Lahan")
	val sanitasiLahan: List<String?>? = null,

	@field:SerializedName("Penggunaan Produk Alami dan Rumah Kaca")
	val penggunaanProdukAlamiDanRumahKaca: List<String?>? = null
) : Parcelable
