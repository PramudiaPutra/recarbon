package com.dextor.recarbon

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.dextor.recarbon.data.SosmedData
import com.dextor.recarbon.databinding.ActivityAddSosmedBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.jar.Manifest

class AddSosmedActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    private lateinit var binding: ActivityAddSosmedBinding
    private lateinit var imgPosting: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSosmedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnKirimPosting.setOnClickListener {
            saveData()
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
            }else{
                Toast.makeText(this, "Izinkan Penggunaan Kamera Dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CAMERA_REQUEST_CODE){
                val thumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap
                binding.ivImage.setImageBitmap(thumbnail)
                imgPosting = thumbnail
            }
        }
    }

    private fun saveData(){
//        val imgUser: Int,
//        val username: String,
//        val location: String,
//        val date: String,
//        val imgStory: Int,
//        val title: String,
//        val content: String

        val imgUser = ContextCompat.getDrawable(this, R.drawable.article_content_image)
        val username = "Yusuf Basqara"
        val location = binding.edtLokasiPoting.text.toString()
        val date = "21-04-2021"
        val imgStory = imgPosting
        val title = binding.edtJudulPoting.text.toString()
        val content = binding.edtDeskripsiPoting.text.toString()

//        val reff = FirebaseDatabase.getInstance().getReference("postingan")
//        val postinganId = reff.push().key
//        val postingan = SosmedData(imgUser, username, location, date, imgStory, title, content)


    }
}

