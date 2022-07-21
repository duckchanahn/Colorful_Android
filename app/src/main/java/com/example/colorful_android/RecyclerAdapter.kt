//package com.example.colorful_android
//
//import android.content.Context
//import android.net.Uri
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//
//class RecyclerAdapter(val context: Context, val boardList: MutableList<DataClass>, val itemClick:(Int, DataClass) -> Unit): RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder<*>>(){
//
//        val TYPE_1 = 0
//        val TYPE_2 = 1
//
//        abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
//                abstract fun bind(item: T)
//        }
//
//        inner class ViewHolder1(itemView:View):BaseViewHolder<DataClass>(itemView){
//                val text = itemView.form_recycler_1_text
//                val btn = itemView.form_recycler_1_btn
//                val img = itemView.form_recycler_1_img
//
//                override fun bind(item: DataClass) {
//                        text?.text = item.string
//                        if(item.image != ""){
//                                img.setImageURI(Uri.parse(item.image))
//                        }
//                        btn.setOnClickListener {
//                                itemClick(adapterPosition,item)
//                        }
//                }
//        }
//
//        inner class ViewHolder2(itemView: View):BaseViewHolder<DataClass>(itemView){
//                val text = itemView.form_recycler_2_text
//                val btn = itemView.form_recycler_2_btn
//                val img = itemView.form_recycler_2_img
//                override fun bind(item: DataClass) {
//                        text?.text = item.string
//                        if(item.image != ""){
//                                img.setImageURI(Uri.parse(item.image))
//                        }
//                        btn.setOnClickListener {
//                                itemClick(adapterPosition,item)
//                        }
//                }
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
//                return when(viewType){
//                        TYPE_1 -> {
//                                val view = LayoutInflater.from(context).inflate(R.layout.form_recyclerview_1, parent, false)
//                                ViewHolder1(view)
//                        }
//                        TYPE_2 -> {
//                                val view = LayoutInflater.from(context).inflate(R.layout.form_recyclerview_2, parent, false)
//                                ViewHolder2(view)
//                        }
//                        else -> throw IllegalArgumentException("Invalid view type")
//                }
//        }
//
//        override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
//                val element = boardList[position]
//                when(holder){
//                        is ViewHolder1 -> holder.bind(element as DataClass)
//                        is ViewHolder2 -> holder.bind(element as DataClass)
//                        else -> throw IllegalArgumentException()
//                }
//        }
//
//        override fun getItemViewType(position: Int): Int {
//                return boardList[position].type
//        }
//
//        override fun getItemCount(): Int {
//                return boardList.size
//        }
//
//}