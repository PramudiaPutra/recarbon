package com.dextor.recarbon.features.home

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.RoundedCornersTransformation
import com.dextor.recarbon.MainActivity
import com.dextor.recarbon.R
import com.dextor.recarbon.model.SosmedData
import com.dextor.recarbon.databinding.ActivitySosmedAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class SosmedAddActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
        private const val REQUEST_CODE = 72
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var list: ArrayList<SosmedData>
    private lateinit var sosmedAdapter: SosmedAdapter
    private lateinit var binding: ActivitySosmedAddBinding

    private lateinit var imgPosting: Bitmap

    private var myUrl = ""
    private var imageUri: Uri? = null

    private val storageReference = FirebaseStorage.getInstance().getReference("uploads")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySosmedAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setImageView()
        initAction()


        list = ArrayList()
        sosmedAdapter = SosmedAdapter(list)

        binding.btnKirimPosting.setOnClickListener {
            saveData2()
//            val intent = Intent(this, MainActivity::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            }
//            startActivity(intent)
        }

        binding.tvBack.setOnClickListener {
            onBackPressed()
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
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } else {
                Toast.makeText(this, "Izinkan Penggunaan Kamera Dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            data?.data?.let {
                imageUri = it
                binding.ivImage.load(imageUri){
                    crossfade(true)
                    crossfade(500)
                    transformations(RoundedCornersTransformation(15f))
                }
            }
       }
    }

    private fun setImageView(){
        binding.ivImage.load(ContextCompat.getDrawable(this, R.drawable.camera_black_icon)){
            crossfade(true)
            crossfade(500)
            transformations(RoundedCornersTransformation(15f))
        }
    }

    private fun initAction(){
        binding.imgFotoPosting.setOnClickListener {
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE)
            }
        }
    }

    private fun uploadImage(title : String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            imageUri?.let {
                storageReference.child(title).putFile(it)
                    .addOnProgressListener {
                        val progres: Int = ((100 * it.bytesTransferred) / it.totalByteCount).toInt()
                        binding.progressBarLoadingIndicator.progress = progres
                        val indicator = "Loading.. $progres%"
                        binding.textViewIndicatorLoading.text = indicator
                    }.await()

                withContext(Dispatchers.Main){
                    Toast.makeText(this@SosmedAddActivity, "Sukses Upload", Toast.LENGTH_SHORT).show()
                }
            }
        }catch (e: Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@SosmedAddActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


//    private fun saveData() {
//
//        //mengambil uid
//        auth = Firebase.auth
//        val currentUser = auth.currentUser
//        val currentUserId = currentUser?.uid
//        Log.d("Id Sekarang", "$currentUserId")
//
//        val uid = currentUserId.toString()
//        val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
//
//        val currentDate = sdf.format(Date())
//        val imgUser = R.drawable.postingan_content_image
//        val username = currentUser?.displayName
//        val location = binding.edtLokasiPoting.text.toString()
//        val date = currentDate
//        val imgStory = imgPosting
//        val title = binding.edtJudulPoting.text.toString()
//        val content = binding.edtDeskripsiPoting.text.toString()
//
//
//        val postingan = SosmedData(
//            imgUser,
//            username,
//            location,
//            date.toString(),
//            imgStory,
//            title,
//            content
//        )
//
//        try{
//            //menyimpan data postingan ke database
//            database = FirebaseDatabase.getInstance().reference
//            database.child("postingan").child(uid).child(database.push().key.toString())
//                .setValue(postingan)
//
//            sosmedAdapter.notifyDataSetChanged()
//        }catch (e: Exception){
//            Log.d("IniPostingan", "$e")
//        }
//
//
//    }

    private fun saveData2() {

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
//        val imgStory = imgPosting
        val title = binding.edtJudulPoting.text.toString()
        val content = binding.edtDeskripsiPoting.text.toString()

//
//        val postingan = SosmedData(
//            imgUser,
//            username,
//            location,
//            date.toString(),
//            imgStory,
//            title,
//            content
//        )

        if (imageUri != null){
            if (title.isBlank() || title.isEmpty()){
                binding.edtJudulPoting.error = "Required"
            }else{
                binding.progressBarLoadingIndicator.isIndeterminate = false
                binding.progressBarLoadingIndicator.visibility = View.VISIBLE
                binding.textViewIndicatorLoading.visibility = View.VISIBLE
                binding.edtJudulPoting.error = null
                uploadImage(title)
            }
        }else{
            Toast.makeText(this@SosmedAddActivity, "Pilih gambar", Toast.LENGTH_SHORT).show()
        }


    }
}

