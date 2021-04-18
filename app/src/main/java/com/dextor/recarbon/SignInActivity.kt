package com.dextor.recarbon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.dextor.recarbon.databinding.ActivitySignInBinding
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

            R.id.tv_ForgotPassword->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_SignIn->{
                loginUser()
            }
        }
    }

    private fun loginUser(){
        val username = binding.edtUsernameSignin.text.toString()
        val password = binding.edtPasswordSignin.text.toString()

        if (TextUtils.isEmpty(username)) {
            binding.edtUsernameSignin.error = "Username harus diisi"
            binding.edtUsernameSignin.requestFocus()
        }

        if (TextUtils.isEmpty(password)) {
            binding.edtUsernameSignin.error = "Password harus diisi"
            binding.edtUsernameSignin.requestFocus()
        }

        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@SignInActivity, "Login Akun Gagal", Toast.LENGTH_LONG).show()
                }
            }

    }
}