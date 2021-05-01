package com.dextor.recarbon

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dextor.recarbon.data.User
import com.dextor.recarbon.databinding.ActivitySignUpBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
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
        binding.tvSignIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_SignUp -> {
                registerUser()
            }

            R.id.tv_SignIn -> {
                val intent = Intent(this, SignInActivity::class.java).apply {
                    flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
            }
        }
    }

    private fun registerUser() {
        val username = binding.edtUsernameSignup.text.toString()
        val password = binding.edtPasswordSignup.text.toString()
        val email = binding.edtEmailSignup.text.toString()

        resetError(binding.edtUsernameSignup)
        resetError(binding.edtEmailSignup)
        resetError(binding.edtPasswordSignup)

        if (TextUtils.isEmpty(username)) {
            binding.tvUsername.error = "username tidak boleh kosong"
        }
//        else if (username.length < 6) {
//            binding.tvUsername.error = "username kurang dari 6 karakter"
//        }

        if (TextUtils.isEmpty(email)) {
            binding.tvEmail.error = "email tidak boleh kosong"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tvEmail.error = "Gunakan Email yang Valid"
//            return
        }

        if (TextUtils.isEmpty(password)) {
            binding.tvPassword.error = "password tidak boleh kosong"
        } else if (password.length !in 13 downTo 5) {
            binding.tvPassword.error = "Jumlah karakter 6 sampai 12"
//            return

        } else {
//            val user = User(username, email, password)
            binding.progressbar.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//                if (it.isSuccessful) {
//                    val currentUser = auth.currentUser
//                    databaseReference?.child(currentUser?.uid!!)?.setValue(user)
//                    Toast.makeText(this@SignUpActivity, "Register Berhasil", Toast.LENGTH_LONG)
//                        .show()
//                    binding.progressbar.visibility = View.GONE
//                    val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
//                    startActivity(intent)
//                    finish()
//
//                } else {
//                    Toast.makeText(this@SignUpActivity, "Register Akun Gagal", Toast.LENGTH_LONG)
//                        .show()
//                    binding.progressbar.visibility = View.GONE
//                }
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser

                    //menambah username
                    val addUsername = UserProfileChangeRequest
                        .Builder()
                        .setDisplayName(username)
                        .build()

                    currentUser?.updateProfile(addUsername)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                this, "Register Berhasil", Toast.LENGTH_LONG
                            ).show()

                            //mencegah auto login setelah register
                            auth.signOut()

                            val intent =
                                Intent(this@SignUpActivity, SignInActivity::class.java).apply {
                                    flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                }
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
                binding.progressbar.visibility = View.GONE
            }
        }
    }

    private fun resetError(editText: TextInputEditText) {
        editText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when (editText) {
                    binding.edtUsernameSignup -> {
                        binding.tvUsername.error = null
                    }
                    binding.edtEmailSignup -> {
                        binding.tvEmail.error = null
                    }
                    binding.edtPasswordSignup -> {
                        binding.tvPassword.error = null
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}