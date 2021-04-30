package com.yongjun.www.starbucks

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.ads.AdSize
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.common.internal.ImagesContract
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.yongjun.www.starbucks.Model.Menus
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menualert.*
import java.lang.Exception
import kotlin.random.Random

class MainActivity : Activity() {
    private val database = Firebase.database
    val mRef = database.getReference("menu")
    var mList = ArrayList<Menus>()
    var mCount = 0

    private lateinit var mAdView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (getNetworkConnected(applicationContext)) {
            listen()
        } else {
            Toast.makeText(applicationContext, R.string.internetFail, Toast.LENGTH_SHORT).show()
        }
        try {
            listen()

        } catch (e: Exception) {
            Toast.makeText(applicationContext, R.string.internetFail, Toast.LENGTH_LONG).show()
        }

        starbucksButton.setOnClickListener {
            showPopup()
        }

        MobileAds.initialize(this){}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
//        var id:String = "ca-app-pub-4294379690418901~5711042749"
//        MobileAds.initialize(this)
//        adView.loadAd(AdRequest.Builder().build())

    }

    private fun showPopup() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.menualert, null)
        val textView: TextView = view.findViewById(R.id.alertTextView)
        var imageView: ImageView = view.findViewById(R.id.alertImageView)
        var randomMenu = mList.random()
        textView.text = randomMenu.name

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("오늘 먹을 스타벅스 메뉴는?")
            .create()

        Picasso.get()
            .load(randomMenu.url)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    Log.d("wht success", randomMenu.url)
                }
                override fun onError(e: Exception?) {
                    Log.d("wht", randomMenu.url)
                }

            })
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            Toast.makeText(applicationContext, randomMenu.name, Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
        }
        alertDialog.setView(view)
        alertDialog.show()
    }

    private fun getNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true

        return isConnected

    }
    private fun listen() {
        mRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    Log.d("hello", snap.toString())
                    snap.getValue<Menus>()?.let { mList.add(it) }
                }
                mCount = mList.size
                Log.d("hello", mList.toString())
                val text = "스타벅스 내용 불러오기에 성공하였습니다 "
                Toast.makeText(applicationContext, R.string.internetSuccess,  Toast.LENGTH_LONG).show()

            }

            override fun onCancelled(error: DatabaseError) {
                val text = "스타벅스 내용 불러오기에 실패하였습니다 \n 인터넷 연결을 확인해주세요"
                Toast.makeText(applicationContext, R.string.internetFail, Toast.LENGTH_LONG).show()
            }
        })

    }
    fun <T> List<T>.random() : T {
        val random = Random.nextInt((size))
        return get(random)
    }



}