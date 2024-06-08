package com.example.florascan.helper

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.example.florascan.R
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ImageClassifierHelper(
    var threshold: Float = 0.1f,
    var maxResults: Int = 3,
    val modelName: String = "cancer_classification.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?,
) {
    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        // TODO: Menyiapkan Image Classifier untuk memproses gambar.
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)
        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4)
        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        val modelFile = File(context.cacheDir, modelName)
        if (!modelFile.exists()) {
            try {
                copyModel(modelName, modelFile)
            } catch (e: IOException) {
                classifierListener?.onError(context.getString(R.string.image_classifier_failed))
                Log.e(TAG, e.message.toString())
                return
            }
        }

        imageClassifier = try {
            ImageClassifier.createFromFileAndOptions(
                modelFile,
                optionsBuilder.build()
            )
        } catch (e: IOException) {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, e.message.toString())
            null
        }
    }
    //biar bisa jalan di emu
    private fun copyModel(modelName: String, modelFile: File) {
        try {
            val inputStream = context.assets.open(modelName)
            val outputStream = FileOutputStream(modelFile)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            throw IOException("Error copy model: ${e.message}")
        }
    }


    fun classifyStaticImage(imageUri: Uri) {
        // TODO: mengklasifikasikan imageUri dari gambar statis.
        if (imageClassifier == null) {
            setupImageClassifier()
        }

        val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri))
        val tensorImage = TensorImage.fromBitmap(bitmap)
        val classifications = imageClassifier?.classify(tensorImage)

        if (classifications != null && classifications.isNotEmpty()) {
            classifierListener?.onResults(classifications, 0)
        } else {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, "Error Classifying Image")
        }
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(
            results: List<Classifications>?,
            inferenceTime: Long,
        )
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }

}