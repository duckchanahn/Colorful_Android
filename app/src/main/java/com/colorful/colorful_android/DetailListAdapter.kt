package com.colorful.colorful_android

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.colorful.colorful_android.R

class DetailListAdapter(val context: Context, val DetaiTourList: ArrayList<TourInfoDetail>) : BaseAdapter() {

    override fun getCount(): Int {
        return DetaiTourList.size
    }

    override fun getItem(position: Int): Any {
        return DetaiTourList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.tour_detail_card_view, null)
        val detailimg = view.findViewById<ImageView>(R.id.detail_img)
        val detailname = view.findViewById<TextView>(R.id.detail_name)
        val detailadress = view.findViewById<TextView>(R.id.detail_adress)

        val tour = DetaiTourList[position]

        detailimg.setImageResource(tour.tour_detail_img)
        detailname.text = tour.tour_detail_name
        detailadress.text = tour.tour_detail_adress

        return view
        //연결이 완료된 뷰를 돌려준다.
    }


}