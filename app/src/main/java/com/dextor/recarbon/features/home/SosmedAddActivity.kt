package com.dextor.recarbon.features.home

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dextor.recarbon.MainActivity
import com.dextor.recarbon.R
import com.dextor.recarbon.model.SosmedData
import com.dextor.recarbon.databinding.ActivitySosmedAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class SosmedAddActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var list: ArrayList<SosmedData>
    private lateinit var sosmedAdapter: SosmedAdapter
    private lateinit var binding: ActivitySosmedAddBinding

    private lateinit var imgPosting: Bitmap


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
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_DENIED
            ) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
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
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } else {
                Toast.makeText(this, "Izinkan Penggunaan Kamera Dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                val thumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap
                binding.ivImage.setImageBitmap(thumbnail)
                imgPosting = thumbnail
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

        //menyimpan data postingan ke database
        database = FirebaseDatabase.getInstance().reference
        database.child("postingan").child(uid).child(database.push().key.toString())
            .setValue(postingan)

        sosmedAdapter.notifyDataSetChanged()

    }
}

