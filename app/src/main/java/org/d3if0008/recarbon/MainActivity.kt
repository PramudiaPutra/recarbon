package org.d3if0008.recarbon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnToSignIn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnToSignIn = findViewById(R.id.btn_toSignIn)
        btnToSignIn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
}