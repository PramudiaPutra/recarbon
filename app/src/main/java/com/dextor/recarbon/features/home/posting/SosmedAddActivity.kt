package com.dextor.recarbon.features.home.posting

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.dextor.recarbon.MainActivity
import com.dextor.recarbon.R
import com.dextor.recarbon.constant.MENU_NAVIGATION
import com.dextor.recarbon.constant.SOSMED_MENU
import com.dextor.recarbon.databinding.ActivitySosmedAddBinding
import com.dextor.recarbon.features.home.SosmedAdapter
import com.dextor.recarbon.model.SosmedData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class SosmedAddActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 2
        private const val CAMERA_REQUEST_CODE = 103
    }

    private lateinit var storageReference: StorageReference

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var list: ArrayList<SosmedData>
    private lateinit var sosmedAdapter: SosmedAdapter
    private lateinit var binding: ActivitySosmedAddBinding

    private lateinit var currentPhotoPath: String
    private lateinit var fileName: String
    private lateinit var contentUri: Uri
    private lateinit var downloadUrl: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySosmedAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        sosmedAdapter = SosmedAdapter(list)

        binding.tvBack.setOnClickListener {
            onBackPressed()
        }

        binding.imgFotoPosting.setOnClickListener {
            cameraPermission()
        }

        binding.btnKirimPosting.setOnClickListener {
            uploadStory()
            binding.progressbar.visibility = View.VISIBLE

        }
    }

    private fun cameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE),
                CAMERA_PERMISSION_CODE
            )
        } else {
            dispatchTakePictureIntent()
        }
    }

//    private fun openCamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, CAMERA_REQUEST_CODE)
//    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
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

                fileName = f.name
                Log.i("filename", fileName)

                contentUri = Uri.fromFile(f)
            }
        }
    }

    private fun uploadStory() {
        storageReference = Firebase.storage.reference.child("uploads").child("camera/$fileName")
        storageReference.putFile(contentUri).addOnSuccessListener {

            storageReference.downloadUrl.addOnSuccessListener { uri ->
                downloadUrl = uri
                Log.i("file url", downloadUrl.toString())
                saveData(downloadUrl)
                binding.progressbar.visibility = View.GONE

                intentHome()
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, "upload failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveData(downloadUrl: Uri) {
        Toast.makeText(this, "upload success", Toast.LENGTH_SHORT).show()

        //mengambil uid
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val currentUserId = currentUser?.uid

        val uid = currentUserId.toString()
        val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())

        val currentDate = sdf.format(Date())
        val imgUser = R.drawable.user_placeholder
        val username = currentUser?.displayName
        val location = binding.edtLokasiPoting.text.toString()
        val imgStory = downloadUrl
        val title = binding.edtJudulPoting.text.toString()
        val content = binding.edtDeskripsiPoting.text.toString()


        val story = SosmedData(
            uid,
            imgUser,
            username,
            location,
            currentDate.toString(),
            imgStory.toString(),
            title,
            content
        )

            //menyimpan data postingan ke database
            database = FirebaseDatabase.getInstance().reference
            database.child("stories").child(database.push().key.toString())
                .setValue(story)

            sosmedAdapter.notifyDataSetChanged()

    }

    private fun intentHome(){
        val intent = Intent(this, MainActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MENU_NAVIGATION, SOSMED_MENU)
        }
        startActivity(intent)
    }
}

