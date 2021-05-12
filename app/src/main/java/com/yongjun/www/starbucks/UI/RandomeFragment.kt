package com.yongjun.www.starbucks.UI

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AlertDialogLayout
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.yongjun.www.starbucks.Model.MenuSingleton
import com.yongjun.www.starbucks.R
import kotlinx.android.synthetic.main.fragment_randome.*
import kotlinx.android.synthetic.main.fragment_randome.view.*
import java.lang.Exception
import kotlin.random.Random


class RandomeFragment : Fragment() {

    private lateinit var mAdView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("random","new")


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_randome, container, false)
        view.randomStarbucksButton.setOnClickListener {


            showPopup()

            Log.d("random","hello")

        }
        Log.d("random", "viewcreate")

        MobileAds.initialize(activity){}
        mAdView =  view.findViewById(R.id.randomADView)

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        return view
    }

    private fun showPopup() {

//        val view = layoutInflater.inflate(R.layout.menualert, null)
//        val textView: TextView = view.findViewById(R.id.alertTextView)
//        val imageView: ImageView = view.findViewById(R.id.alertImageView)
//        var randomeMenu = MenuSingleton.menus.random()
//        textView.text = randomeMenu.name

        val randomeMenu = MenuSingleton.menus.random()
        val builderItem = AlertDialogLayout.inflate(activity, R.layout.menualert, null)
        val textView = builderItem.findViewById<TextView>(R.id.alertTextView)
        val imageView: ImageView = builderItem.findViewById(R.id.alertImageView)

        textView.text = randomeMenu.name
        val alertDialog = AlertDialog.Builder(activity)
            .setTitle("스벅신이 추천하는 메뉴는?")
            .create()

        activity?.let {
            Glide.with(it)
                .load(randomeMenu.url)
                .into(imageView)
        }
//        Picasso.get()
//            .load(randomeMenu.url)
//            .into(imageView, object : Callback {
//                override fun onSuccess() {
//                }
//
//                override fun onError(e: Exception?) {
//                }
//
//            })

        val saveButton = builderItem.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.setView(builderItem)
        alertDialog.show()
    }


    fun <T> List<T>.random() : T {
        val random = Random.nextInt((size))
        return get(random)
    }
}