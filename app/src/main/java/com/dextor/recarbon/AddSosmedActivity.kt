package com.dextor.recarbon

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
import com.dextor.recarbon.adapter.SosmedAdapter
import com.dextor.recarbon.data.SosmedData
import com.dextor.recarbon.databinding.ActivityAddSosmedBinding
import com.dextor.recarbon.fragment.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class AddSosmedActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    private lateinit var list: ArrayList<SosmedData>
    private lateinit var sosmedAdapter: SosmedAdapter
    private lateinit var binding: ActivityAddSosmedBinding

    private lateinit var imgPosting: Bitmap
    lateinit var auth: FirebaseAuth
    private var databaseReference: DatabaseReference? = null
    private var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSosmedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        sosmedAdapter = SosmedAdapter(list)
        val homeFragment = HomeFragment()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("users")

        binding.btnKirimPosting.setOnClickListener {
            saveData()
            val intent = Intent(this, MainActivity::class.java)
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

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)
        var name = ""

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                name = snapshot.child("username").value.toString()
                Log.d("SettingsFragment", "Username Awal: $name")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        val imgUser = R.drawable.postingan_content_image
        val username = "Yusuf Basqara"
        val location = binding.edtLokasiPoting.text.toString()
        val date = Calendar.DATE
        val imgStory = imgPosting
        val title = binding.edtJudulPoting.text.toString()
        val content = binding.edtDeskripsiPoting.text.toString()

        HomeFragment.list.add(SosmedData(imgUser,username,location,date.toString(),imgStory, title,content))
        sosmedAdapter.notifyDataSetChanged()

    }
}

