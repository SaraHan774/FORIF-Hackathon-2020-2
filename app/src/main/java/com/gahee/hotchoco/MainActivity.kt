package com.gahee.hotchoco

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.gahee.hotchoco.databinding.ActivityMainBinding
import com.gahee.hotchoco.model.MarshMallow
import com.gahee.hotchoco.room.MarshViewModel
import com.gahee.hotchoco.room.MarshViewModelFactory
import com.gahee.hotchoco.room.MyApplication
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    var marshImageList = arrayListOf<ImageView>()
    lateinit var binding : ActivityMainBinding
    lateinit var marshmallow : String

    private val marshViewModel: MarshViewModel by viewModels {
        MarshViewModelFactory((application as MyApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        marshImageList.add(binding.m1)
        marshImageList.add(binding.m2)
        marshImageList.add(binding.m3)
        marshImageList.add(binding.m4)
        marshImageList.add(binding.m5)
        marshImageList.add(binding.m6)
        marshImageList.add(binding.m7)

        binding.mainFab.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@MainActivity, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView: View = LayoutInflater
                .from(applicationContext)
                .inflate(R.layout.bottom_dialog, findViewById(R.id.bottom_sheet_container))
            bottomSheetDialog.setContentView(bottomSheetView)

            val sendBtn = bottomSheetDialog.findViewById<Button>(R.id.dialogSendBtn)
            val inputBox = bottomSheetDialog.findViewById<EditText>(R.id.dialogInput)

            sendBtn?.setOnClickListener {
                inputBox?.let { input ->
                    marshmallow = input.text.toString()
                    marshViewModel.insert(
                        MarshMallow(
                        date = Calendar.getInstance().timeInMillis.toString(),
                        content = marshmallow
                    )
                    )
                }
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.show()
        }

        marshViewModel.allMarshMallows.observe(this, {
            val prefs = getSharedPreferences("appPref", MODE_PRIVATE)
            val isFirstOpen = prefs.getBoolean("isFirstOpen", true)

            val remainder = (it.size % 7)
            if (remainder != 0) {
                if(isFirstOpen){
                    for (x in 0 until remainder){
                        marshImageList[x].visibility = View.VISIBLE
                    }
                    prefs.edit().putBoolean("isFirstOpen", false)
                }
                marshImageList[remainder - 1].visibility = View.VISIBLE
                dropAndRotate2(marshImageList[remainder - 1])
            } else {
                val mySnackbar = Snackbar.make(
                    binding.mainContainer,
                    "\uD83E\uDD0E Hot Chocolate is ready! ❤", Snackbar.LENGTH_LONG
                )
                marshImageList.forEach { imageView ->
                    imageView.visibility = View.GONE
                }
                mySnackbar.setAction("CHECK") {
                    startActivity(Intent(this@MainActivity, MyPageActivity::class.java))
                }
                mySnackbar.show()
            }
        })
    }
    //end of onCreate

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.mypage){
            startActivity(Intent(this@MainActivity, MyPageActivity::class.java))
        }
        return true
    }

    private fun dropAndRotate2(view : ImageView){
        val kf0 = Keyframe.ofFloat(0f, -400f)
        val kf1 = Keyframe.ofFloat(.3f, -200f)
        val kf3 = Keyframe.ofFloat(.6f, -100f)
        val kf2 = Keyframe.ofFloat(1f, 0f)

        val pvhRotation = PropertyValuesHolder.ofKeyframe("translationY", kf0, kf1, kf3, kf2)
        ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            duration = 4000
            start()
        }

        val kf4 = Keyframe.ofFloat(0f, 80f)
        val kf5 = Keyframe.ofFloat(.3f, 120f)
        val kf6 = Keyframe.ofFloat(.7f, 180f)
        val kf7 = Keyframe.ofFloat(1f, Random.nextFloat() * (180 - 30) + 30)
        val rotation = PropertyValuesHolder.ofKeyframe("rotation", kf4, kf5, kf6, kf7)
        ObjectAnimator.ofPropertyValuesHolder(view, rotation).apply {
            duration = 4000
            start()
        }
    }

    companion object{
        const val TAG = "MainActivity"
    }
}