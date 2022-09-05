package com.example.colorful_android

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(val context: Context, val TourList: ArrayList<TourInfo>) : BaseAdapter() {
    override fun getCount(): Int {
        return TourList.size
    }

    override fun getItem(position: Int): Any {
        return TourList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.tour_card_view, null)
        val cardimg = view.findViewById<ImageView>(R.id.card_img)
        val tourname = view.findViewById<TextView>(R.id.tour_name)
        val tourdate = view.findViewById<TextView>(R.id.tour_date)
        val tourcount = view.findViewById<TextView>(R.id.tour_count)

        val tour = TourList[position]

        cardimg.setImageResource(tour.tour_img)
        tourname.text = tour.tour_name
        tourdate.text = tour.tour_date
        tourcount.text = tour.tour_count

        return view
        //연결이 완료된 뷰를 돌려준다.
    }

}