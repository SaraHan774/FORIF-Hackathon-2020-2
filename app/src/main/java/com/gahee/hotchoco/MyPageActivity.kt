package com.gahee.hotchoco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gahee.hotchoco.room.MarshViewModel
import com.gahee.hotchoco.room.MarshViewModelFactory
import com.gahee.hotchoco.room.MyApplication


class MyPageActivity : AppCompatActivity() {

    private val marshViewModel: MarshViewModel by viewModels {
        MarshViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        val list = findViewById<RecyclerView>(R.id.myPageList)
        val listAdapter = MyPageListAdapter(null)

        list.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(this@MyPageActivity)
        }

        marshViewModel.allMarshMallows.observe(this, { marshMallows ->
            listAdapter.updateList(marshMallows)
        })
    }
}