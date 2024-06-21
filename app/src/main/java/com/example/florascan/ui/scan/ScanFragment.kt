package com.example.florascan.ui.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.florascan.R
import com.example.florascan.ResultActivity
import com.example.florascan.databinding.FragmentScanBinding
import com.example.florascan.factory.AuthViewModelFactory
import com.example.florascan.helper.ResponseMl
import com.example.florascan.result.Result
import com.example.florascan.utils.getImageUri
import com.example.florascan.utils.reduceFileImage
import com.example.florascan.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class LScanFragment : Fragment() {

    private lateinit var binding: FragmentScanBinding
    private var currentImageUri: Uri? = null

    private val viewModel: ScanViewModel by viewModels {
        AuthViewModelFactory(requireContext())
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Permission request granted")
            } else {
                showToast("Permission request denied")
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener {
            currentImageUri?.let {
                uploadImage() // Call the uploadImage function here
            } ?: run {
                showToast(getString(R.string.empty_image_warning))
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext()) // Pass context here
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    //    private fun uploadImage() {
//        currentImageUri?.let { uri ->
//            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
//            Log.d("Image Classification File", "showImage: ${imageFile.path}")
//            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//            val multipartBody = MultipartBody.Part.createFormData(
//                "photo",
//                imageFile.name,
//                requestImageFile
//            )
//            lifecycleScope.launch {
//                try {
//                    val apiService = ApiConfigMl.getApiService()
//                    val successResponse = apiService.uploadImage(multipartBody)
//                    successResponse.result?.let {
//                        val confidenceScore = it.score ?: 0.0
//                        val resultText = if (confidenceScore > 0.5) {
//                            showToast(successResponse.message.toString())
//                            with(String) { format("%s with %.2f%%", it.prediction, confidenceScore * 100) }
//                        } else {
//                            showToast("Model is predicted successfully but under threshold.")
//                            String.format("Please use the correct picture because the confidence score is %.2f%%", confidenceScore * 100)
//                        }
//                        moveToResult(true, resultText)
//                    } ?: run {
//                        moveToResult(false, "Prediction failed")
//                    }
//                } catch (e: HttpException) {
//                    val errorBody = e.response()?.errorBody()?.string()
//                    val errorResponse = Gson().fromJson(errorBody, ResponseMl::class.java)
//                    showToast(errorResponse.message.toString())
//                    moveToResult(false, "HTTP Exception occurred")
//                }
//            }
//        } ?: showToast(getString(R.string.empty_image_warning))
//    }
    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            Log.d("Image Classification File", "showImage: ${imageFile.path}")
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )

            viewModel.uploadImage(multipartBody).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                 when (response) {
                     is Result.Error -> {
                         binding.progressBar.visibility = View.GONE
                         Toast.makeText(requireContext(), response.error, Toast.LENGTH_SHORT).show()
                     }
                     Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                     is Result.Success -> {
                         binding.progressBar.visibility = View.GONE
                         moveToResult(uri, response.data)
                     }
                 }                 }
                }

        } ?: showToast(getString(R.string.empty_image_warning))
    }


    private fun moveToResult(imageUri: Uri, result: ResponseMl) {
        val intent = Intent(requireContext(), ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, imageUri.toString())
        intent.putExtra(ResultActivity.EXTRA_RESULT, result)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}


//package com.example.florascan.ui.scan
//
//import android.Manifest
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.activity.result.PickVisualMediaRequest
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.Fragment
//import com.example.florascan.R
//import com.example.florascan.ResultActivity
//import com.example.florascan.databinding.FragmentScanBinding
//import com.example.florascan.helper.ResponseMl
//import com.example.florascan.helper.Suggestion
//import com.example.florascan.utils.getImageUri
//import com.google.gson.Gson
//import java.text.NumberFormat
//
//class ScanFragment : Fragment() {
//
//    private lateinit var binding: FragmentScanBinding
//    private var currentImageUri: Uri? = null
//
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//            if (isGranted) {
//                showToast("Permission request granted")
//            } else {
//                showToast("Permission request denied")
//            }
//        }
//
//    private fun allPermissionsGranted() =
//        ContextCompat.checkSelfPermission(
//            requireContext(),
//            REQUIRED_PERMISSION
//        ) == PackageManager.PERMISSION_GRANTED
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentScanBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        if (!allPermissionsGranted()) {
//            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
//        }
//        binding.cameraButton.setOnClickListener { startCamera() }
//        binding.galleryButton.setOnClickListener { startGallery() }
//        binding.analyzeButton.setOnClickListener {
//            currentImageUri?.let {
//                analyzeImage(it)
//            } ?: run {
//                showToast(getString(R.string.empty_image_warning))
//            }
//        }
//    }
//
//    private fun startGallery() {
//        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//    }
//
//    private val launcherGallery = registerForActivityResult(
//        ActivityResultContracts.PickVisualMedia()
//    ) { uri: Uri? ->
//        if (uri != null) {
//            currentImageUri = uri
//            showImage()
//        } else {
//            Log.d("Photo Picker", "No media selected")
//        }
//    }
//
//    private fun startCamera() {
//        currentImageUri = getImageUri(requireContext()) // Pass context here
//        launcherIntentCamera.launch(currentImageUri)
//    }
//
//    private val launcherIntentCamera = registerForActivityResult(
//        ActivityResultContracts.TakePicture()
//    ) { isSuccess ->
//        if (isSuccess) {
//            showImage()
//        }
//    }
//
//    private fun showImage() {
//        currentImageUri?.let {
//            Log.d("Image URI", "showImage: $it")
//            binding.previewImageView.setImageURI(it)
//        }
//    }
//
//    private fun analyzeImage(uri: Uri) {
//        // Mock implementation of image analysis
//        // Replace with actual implementation to generate ResponseMl
//        val responseMl = getMockResponseMl(uri)
//        handleAnalysisResults(responseMl, uri)
//    }
//
//    private fun handleAnalysisResults(responseMl: ResponseMl?, uri: Uri) {
//        responseMl?.let {
//            val resultJson = Gson().toJson(it)
//            moveToResult(uri, resultJson)
//        } ?: run {
//            showToast(getString(R.string.empty_image_warning))
//        }
//    }
//
//    private fun moveToResult(imageUri: Uri, result: String) {
//        val intent = Intent(requireContext(), ResultActivity::class.java)
//        intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, imageUri.toString())
//        intent.putExtra(ResultActivity.EXTRA_RESULT, result)
//        startActivity(intent)
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//    }
//
//    companion object {
//        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
//    }
//
//    // Mock method to generate a ResponseMl object
//    private fun getMockResponseMl(uri: Uri): ResponseMl {
//        val suggestion = Suggestion(
//            cabutDanMusnahkanBagianYangTerinfeksi = listOf("Remove infected parts"),
//            pengelolaanLingkungan = listOf("Manage environment"),
//            identifikasiTanamanYangTerinfeksi = listOf("Identify infected plants"),
//            pemantauanDanPerawatanLanjutan = listOf("Monitor and follow-up care"),
//            aplikasiBakterisida = listOf("Apply bactericide")
//        )
//
//        val result = com.example.florascan.helper.Result(
//            score = 0.95f, // Example score
//            suggestion = suggestion,
//            prediction = "Healthy Plant" // Example prediction
//        )
//
//        return ResponseMl(
//            result = result,
//            message = "Analysis completed",
//            status = "Success"
//        )
//    }
//}
