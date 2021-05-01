package com.dextor.recarbon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.dextor.recarbon.databinding.ActivitySignInBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvSignUp.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
        binding.btnSignIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_SignUp -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }

            R.id.tv_ForgotPassword -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_SignIn -> {
                loginUser()
            }
        }
    }

    //mengecek apakah user sudah pernah login atau belum
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        if (currentUser != null){
            startActivity(Intent(this@SignInActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun loginUser() {
        val username = binding.edtUsernameSignin.text.toString()
        val password = binding.edtPasswordSignin.text.toString()

        resetError(binding.edtUsernameSignin)
        resetError(binding.edtPasswordSignin)

        when {
            TextUtils.isEmpty(username) -> {
                binding.tvUsername.error = "Username harus diisi"
                if (TextUtils.isEmpty(password)) {
                    binding.tvPassword.error = "Password harus diisi"
                }

            }
            TextUtils.isEmpty(password) -> {
                binding.tvPassword.error = "Password harus diisi"
                if (TextUtils.isEmpty(username)) {
                    binding.tvUsername.error = "Username harus diisi"

                }

            }
            else -> {
                binding.progressbar.visibility = View.VISIBLE

                auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()

                        }
                        binding.progressbar.visibility = View.GONE
                    }
            }
        }

    }

    private fun resetError(editText: TextInputEditText) {
        editText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editText == binding.edtUsernameSignin) {
                    binding.tvUsername.error = null
                } else if (editText == binding.edtPasswordSignin) {
                    binding.tvPassword.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}