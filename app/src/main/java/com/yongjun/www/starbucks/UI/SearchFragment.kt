package com.yongjun.www.starbucks.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.yongjun.www.starbucks.Adapter.CustomAdapter
import com.yongjun.www.starbucks.Model.MenuSingleton
import com.yongjun.www.starbucks.R
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdView: AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.searchRecyclerView)
        val mAdapter = context?.let { CustomAdapter(it, MenuSingleton.menus) }
        recyclerView.adapter = mAdapter

        val layout = LinearLayoutManager(activity)
        recyclerView.layoutManager = layout
        recyclerView.setHasFixedSize(true)
        MobileAds.initialize(activity){}
        mAdView =  view.findViewById(R.id.randomADView)

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        return  view
    }

}