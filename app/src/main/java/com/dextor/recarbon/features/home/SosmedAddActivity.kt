package com.dextor.recarbon.features.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.dextor.recarbon.MainActivity
import com.dextor.recarbon.R
import com.dextor.recarbon.model.SosmedData
import com.dextor.recarbon.databinding.ActivitySosmedAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class SosmedAddActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val CAMERA_PERMISSION_CODE = 2
        private const val CAMERA_REQUEST_CODE = 103
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var list: ArrayList<SosmedData>
    private lateinit var sosmedAdapter: SosmedAdapter
    private lateinit var binding: ActivitySosmedAddBinding

    private lateinit var imgPosting: Bitmap
    private lateinit var currentPhotoPath: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySosmedAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        sosmedAdapter = SosmedAdapter(list)

        binding.btnKirimPosting.setOnClickListener {
            saveData()
            val intent = Intent(this, MainActivity::class.java).apply {
                flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }

        binding.tvBack.setOnClickListener {
            onBackPressed()
        }

        binding.imgFotoPosting.setOnClickListener {
            cameraPermission()
        }
    }

    private fun cameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        } else {
            dispatchTakePictureIntent()
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.dextor.recarbon.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(this, "Izinkan Penggunaan Kamera Dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val f = File(currentPhotoPath)
                binding.ivImage.setImageURI(Uri.fromFile(f))
                Log.i("fileUri", Uri.fromFile(f).toString())
            }
        }
    }


    private fun saveData() {

        //mengambil uid
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val currentUserId = currentUser?.uid
        Log.d("Id Sekarang", "$currentUserId")

        val uid = currentUserId.toString()
        val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())

        val currentDate = sdf.format(Date())
        val imgUser = R.drawable.postingan_content_image
        val username = currentUser?.displayName
        val location = binding.edtLokasiPoting.text.toString()
        val date = currentDate
        val imgStory = imgPosting
        val title = binding.edtJudulPoting.text.toString()
        val content = binding.edtDeskripsiPoting.text.toString()


        val postingan = SosmedData(
            imgUser,
            username,
            location,
            date.toString(),
            imgStory,
            title,
            content
        )

        try {
            //menyimpan data postingan ke database
            database = FirebaseDatabase.getInstance().reference
            database.child("postingan").child(uid).child(database.push().key.toString())
                .setValue(postingan)

            sosmedAdapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Log.d("IniPostingan", "$e")
        }


    }
}

