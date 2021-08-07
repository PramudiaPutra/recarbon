package com.dextor.recarbon.features.home.posting

import com.dextor.recarbon.R
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dextor.recarbon.databinding.ActivityCameraBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity() {
    private var imageCapture: ImageCapture? = null
    private var preview: Preview? = null
    private var selectedCamera = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var outputDirectory: File

    //    private lateinit var cameraExecutorService: ExecutorService
    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(binding.root)

        requestingPermissions()
        outputDirectory = getOutputDirectory()

        with(binding) {
            cameraButton.setOnClickListener {
                //take picture effect
                takePictureEffect.visibility = View.VISIBLE
                takePictureEffect.postDelayed({ takePictureEffect.visibility = View.GONE }, 200L)

                val bitmap = binding.cameraPreview.bitmap
                if (bitmap != null) getCapturedPreview(bitmap)
//            takePhoto()
            }

            switchCameraButton.setOnClickListener {
                selectedCamera = if (selectedCamera == CameraSelector.DEFAULT_BACK_CAMERA) {
                    CameraSelector.DEFAULT_FRONT_CAMERA
                } else {
                    CameraSelector.DEFAULT_BACK_CAMERA
                }
                startCamera()
            }

            backButton.setOnClickListener {
                if (capturedPreview.visibility != View.GONE) {
                    with(binding) {
                        //View Visible
                        cameraPreview.visibility = View.VISIBLE
                        cameraButton.visibility = View.VISIBLE
                        switchCameraButton.visibility = View.VISIBLE

                        //View Gone
                        capturedPreview.visibility = View.GONE
                        backButton.visibility = View.GONE
                        confirmButton.visibility = View.GONE
                    }
                }
            }

            exitButton.setOnClickListener { onBackPressed() }
        }
    }


    private fun getCapturedPreview(bitmap: Bitmap) {
        with(binding) {

            //View Gone
            cameraPreview.visibility = View.GONE
            cameraButton.visibility = View.GONE
            switchCameraButton.visibility = View.GONE

            //View Visible
            capturedPreview.visibility = View.VISIBLE
            backButton.visibility = View.VISIBLE
            confirmButton.visibility = View.VISIBLE

            //Set captured image
            capturedPreview.setImageBitmap(bitmap)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            preview = Preview
                .Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
                }

            imageCapture = ImageCapture
                .Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, selectedCamera, preview, imageCapture)
            } catch (e: Exception) {
                Log.e("camera failed", "error", e)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(photoFile)
            .build()

        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {

                override fun onError(e: ImageCaptureException) {
                    Log.e("capture failure", "error: ${e.message}", e)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo captured successfully: $savedUri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        cameraExecutorService.shutdown()
//    }

    private fun requestingPermissions() {
        if (permissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSION)
        }
    }

    private fun permissionsGranted(): Boolean = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION && permissionsGranted()) {
            startCamera()
        } else {
            Toast.makeText(this, "camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 101
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }
}