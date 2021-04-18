package com.dextor.recarbon

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dextor.recarbon.data.User
import com.dextor.recarbon.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private var databaseReference: DatabaseReference? = null
    private var database: FirebaseDatabase? = null
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("users")

        binding.btnSignUp.setOnClickListener(this)
        binding.tvSignIn.setOnClickListener (this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_SignUp -> {
                registerUser()
            }

            R.id.tv_SignIn -> {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun registerUser() {
        val username = binding.edtUsernameSignup.text.toString()
        if (username.length < 6) {
            binding.edtUsernameSignup.error = "Jumlah karakter Minimal 6"
            binding.edtUsernameSignup.requestFocus()
        }

        val email = binding.edtEmailSignup.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmailSignup.error = "Gunakan Email yang Valid"
            binding.edtEmailSignup.requestFocus()
            return
        }

        val password = binding.edtPasswordSignup.text.toString()
        if (password.length !in 13 downTo 5) {
            binding.edtPasswordSignup.error = "Jumlah karakter 6 samopai 12"
            binding.edtPasswordSignup.requestFocus()
            return
        }

        val user = User(username, email, password)
        binding.progressbar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val currentUser = auth.currentUser
                databaseReference?.child(currentUser?.uid!!)?.setValue(user)
                Toast.makeText(this@SignUpActivity, "Register Berhasil", Toast.LENGTH_LONG).show()
                binding.progressbar.visibility = View.GONE
                finish()

            } else {
                Toast.makeText(this@SignUpActivity, "Register Akun Gagal", Toast.LENGTH_LONG).show()
                binding.progressbar.visibility = View.GONE
            }
        }

    }
}