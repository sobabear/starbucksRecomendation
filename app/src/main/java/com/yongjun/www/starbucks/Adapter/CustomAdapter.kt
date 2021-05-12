package com.yongjun.www.starbucks.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.yongjun.www.starbucks.Model.Menus
import com.yongjun.www.starbucks.R
import java.lang.Exception

class CustomAdapter(private val context: Context, private val dateList: ArrayList<Menus>): RecyclerView.Adapter<CustomAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val coffeeImageView = itemView.findViewById<ImageView>(R.id.searchCoffeeImageView)
        private val coffeeTextView = itemView.findViewById<TextView>(R.id.searchCoffeeName)

        fun bind(menu: Menus, context: Context) {
            Glide.with(context)
                .load(menu.url)
                .into(coffeeImageView)
//            Picasso.get()
//                .load(menu.url)
//                .into(coffeeImageView, object : Callback {
//                    override fun onError(e: Exception?) {
//                    }
//
//                    override fun onSuccess() {
//                    }
//                })
            coffeeTextView.text = menu.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout, parent, false)
        return ItemViewHolder(view)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dateList[position], context)
    }
    override fun getItemCount(): Int {
        return dateList.size
    }
}