package com.dextor.recarbon.features.home.posting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dextor.recarbon.MainActivity
import com.dextor.recarbon.R
import com.dextor.recarbon.constant.MENU_NAVIGATION
import com.dextor.recarbon.constant.IMAGE_PATH
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
import java.text.SimpleDateFormat
import java.util.*


class SosmedAddActivity : AppCompatActivity() {

    private lateinit var storageReference: StorageReference

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var list: ArrayList<SosmedData>
    private lateinit var sosmedAdapter: SosmedAdapter
    private lateinit var binding: ActivitySosmedAddBinding

    private lateinit var imagePath: String
    private lateinit var fileName: String
    private lateinit var imageUri: Uri
    private lateinit var imageUrl: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySosmedAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //getting image path from camera
        imagePath = intent.getStringExtra(IMAGE_PATH).toString()
        imageUri = Uri.parse(imagePath)
        binding.storyImage.setImageURI(imageUri)

        //get filename from image uri
        fileName = File(imageUri.path).name

        list = ArrayList()
        sosmedAdapter = SosmedAdapter(list)

        binding.tvBack.setOnClickListener {
            onBackPressed()
        }

        binding.postingButton.setOnClickListener {
            uploadStory()
            binding.progressbar.visibility = View.VISIBLE

        }
    }

    private fun uploadStory() {
        storageReference = Firebase.storage.reference.child("uploads").child("camera/$fileName")
        storageReference.putFile(imageUri).addOnSuccessListener {

            storageReference.downloadUrl.addOnSuccessListener { uri ->
                imageUrl = uri
                Log.i("file url", imageUrl.toString())
                uploadData(imageUrl)
                binding.progressbar.visibility = View.GONE

                intentHome()
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, "upload failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadData(downloadUrl: Uri) {
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
        val title = binding.edtJudulPoting.text.toString()
        val content = binding.edtDeskripsiPoting.text.toString()


        val story = SosmedData(
            uid,
            imgUser,
            username,
            location,
            currentDate.toString(),
            downloadUrl.toString(),
            title,
            content
        )

            //menyimpan data postingan ke database
            database = FirebaseDatabase.getInstance().reference
            database.child("stories").child(database.push().key.toString())
                .setValue(story)

//            sosmedAdapter.notifyDataSetChanged()

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
