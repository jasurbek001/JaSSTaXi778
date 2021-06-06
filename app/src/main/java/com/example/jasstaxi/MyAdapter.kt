package com.example.jasstaxi

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jasstaxi.databinding.BottomsheetLayoutBinding

class MyAdapter(internal var context: Context) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    lateinit var bindingBottomsheetLayoutBinding:BottomsheetLayoutBinding


    private lateinit var string: String
    private var buyurtList: MutableList<BuyurtmaOlish> = ArrayList()

    val intent = Intent(context, TaksoMetr::class.java)
    val listItemId: String?
        get() = buyurtList[buyurtList.size - 1].numberPhone


    fun add(newsZakaz: List<BuyurtmaOlish>) {
        val init: Int = buyurtList.size
        buyurtList.addAll(newsZakaz)
        notifyItemRangeChanged(init, newsZakaz.size)
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var txt_adress: TextView = itemView.findViewById<TextView>(R.id.buytxt)
        internal var txt_phone: TextView = itemView.findViewById<TextView>(R.id.phonetxt)
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

        holder.itemView.findViewById<Button>(R.id.olbtn).setOnClickListener {
            val context = holder.txt_adress.context
            intent.putExtra("aaa", buyurtList[position].numberPhone)
            showDialog3()
        }




    }
    private fun showDialog3() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        bindingBottomsheetLayoutBinding = BottomsheetLayoutBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(bindingBottomsheetLayoutBinding!!.root)



        bindingBottomsheetLayoutBinding.textView.text = "Haqiqatdan ham chiqmoqchimisiz"

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.attributes?.windowAnimations = R.style.DialoAnimation
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)


        bindingBottomsheetLayoutBinding.botOkBtn.text = "10"
        bindingBottomsheetLayoutBinding.botNoBtn.text = "20"

        bindingBottomsheetLayoutBinding.botNoBtn.setOnClickListener {
            intent.putExtra("a","20")
            dialog.dismiss()
            context.startActivity(intent)
        }

        bindingBottomsheetLayoutBinding.botOkBtn.setOnClickListener {
            intent.putExtra("a","10")
            dialog.dismiss()
            context.startActivity(intent)
        }

    }
    override fun getItemCount(): Int {
        return buyurtList.size
    }
}