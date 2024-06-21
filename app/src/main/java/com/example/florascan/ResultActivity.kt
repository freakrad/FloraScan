package com.example.florascan

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.example.florascan.databinding.ActivityResultBinding
import com.example.florascan.helper.ResponseMl

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
        }

        val responseMl = intent.getParcelableExtra<ResponseMl>(EXTRA_RESULT)

        responseMl?.let {
            displayResult(it)
        }
    }

    private fun displayResult(responseMl: ResponseMl) {
        val resultText = buildString {
            append("<b>Message:</b> ${responseMl.message}<br>")
            append("<b>Status:</b> ${responseMl.status}<br>")
            append("<b>Prediction:</b> ${responseMl.result?.prediction}<br>")
            append("<b>Score:</b> ${responseMl.result?.score}<br>")
        }

        val suggestionText = buildString {
            responseMl.result?.suggestion?.let { suggestion ->
                append("<b>Sanitasi Lahan:</b><br>${suggestion.sanitasiLahan?.joinToString(", ")}<br><br>")
                append("<b>Penggunaan Varietas Tahan Penyakit:</b><br>${suggestion.penggunaanVarietasTahanPenyakit?.joinToString(", ")}<br><br>")
                append("<b>Pengaturan Waktu dan Jarak Tanam:</b><br>${suggestion.pengaturanWaktuDanJarakTanam?.joinToString(", ")}<br><br>")
                append("<b>Pengendalian Hayati:</b><br>${suggestion.pengendalianHayati?.joinToString(", ")}<br><br>")
                append("<b>Perawatan Benih:</b><br>${suggestion.perawatanBenih?.joinToString(", ")}<br><br>")
                append("<b>Penggunaan Fungisida:</b><br>${suggestion.penggunaanFungisida?.joinToString(", ")}<br><br>")
                append("<b>Aplikasi Agen Pelapis Organik:</b><br>${suggestion.aplikasiAgenPelapisOrganik?.joinToString(", ")}<br><br>")
                append("<b>Pendekatan Terpadu:</b><br>${suggestion.pendekatanTerpadu?.joinToString(", ")}<br><br>")
                append("<b>Penggunaan Produk Alami dan Rumah Kaca:</b><br>${suggestion.penggunaanProdukAlamiDanRumahKaca?.joinToString(", ")}<br><br>")
                append("<b>Perawatan Daun dan Penyemprotan Fungisida:</b><br>${suggestion.perawatanDaunDanPenyemprotanFungisida?.joinToString(", ")}<br><br>")
            }
        }

        binding.resultText.text = HtmlCompat.fromHtml(resultText, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.suggestionText.text = HtmlCompat.fromHtml(suggestionText, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }
}
