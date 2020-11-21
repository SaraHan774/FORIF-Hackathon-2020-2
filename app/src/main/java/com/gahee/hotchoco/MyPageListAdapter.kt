package com.gahee.hotchoco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gahee.hotchoco.model.MarshMallow
import java.text.SimpleDateFormat
import java.util.*

class MyPageListAdapter(
    var marshList : List<MarshMallow>?
) : RecyclerView.Adapter<MyPageViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_view_holder, parent, false)
        return MyPageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPageViewHolder, position: Int) {
        marshList?.get(position)?.let { holder.bind(it) }
    }

    fun updateList(marshList: List<MarshMallow>){
        this.marshList = marshList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if(marshList == null){ 0 } else{ marshList?.size!!}
    }
}

class MyPageViewHolder(
    itemView : View
) : RecyclerView.ViewHolder(itemView){

    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    val calendar = Calendar.getInstance()

    fun bind(marshMallow: MarshMallow) {
        calendar.timeInMillis = marshMallow.date.toLong()
        itemView.findViewById<TextView>(R.id.marshDate).text = simpleDateFormat.format(calendar.time)
        itemView.findViewById<TextView>(R.id.marshContent).text = marshMallow.content
    }

}