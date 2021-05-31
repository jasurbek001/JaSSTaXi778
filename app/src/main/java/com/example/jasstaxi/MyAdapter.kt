package com.example.jasstaxi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(internal var context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    private lateinit var string: String
    internal var buyurtList: MutableList<BuyurtmaOlish>

    val listItemId: String?
        get() = buyurtList[buyurtList.size - 1].numberPhone


    fun add(newsZakaz: List<BuyurtmaOlish>) {
        val init: Int = buyurtList.size
        buyurtList.addAll(newsZakaz)
        notifyItemRangeChanged(init, newsZakaz.size)
    }


    init {
        this.buyurtList = ArrayList()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var txt_adress: TextView
        internal var txt_phone: TextView


        init {
            txt_adress = itemView.findViewById<TextView>(R.id.buytxt)
            txt_phone = itemView.findViewById<TextView>(R.id.phonetxt)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(
            R.layout.list_layout,
            parent,
            false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.txt_phone.text = buyurtList[position].numberPhone
        holder.txt_adress.text = buyurtList[position].zakazAdress
        string = buyurtList[position].numberPhone
        holder.itemView.findViewById<Button>(R.id.olbtn).setOnClickListener { v: View ->
            Unit
            val context = holder.txt_adress.context

            val intent = Intent(context, taksoMetr::class.java)
            intent.putExtra("aaa",buyurtList[position].numberPhone)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return buyurtList.size
    }


}