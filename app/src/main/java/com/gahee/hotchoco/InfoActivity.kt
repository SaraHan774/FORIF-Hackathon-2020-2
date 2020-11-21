package com.gahee.hotchoco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.gahee.hotchoco.Constants.Companion.USER_NAME_KEY
import com.gahee.hotchoco.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    lateinit var binding : ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_info
        )

        val userName = intent.getStringExtra(USER_NAME_KEY)
        userName?.let {name ->
            binding.nicknameField.setText(name)
        }

        binding.startBtn.setOnClickListener{
            // TODO: 11/20/2020 save user information
            startActivity(Intent(this@InfoActivity, MainActivity::class.java))
            finish()
        }
    }
}