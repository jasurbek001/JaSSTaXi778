package com.example.jasstaxi.adapters

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.jasstaxi.R
import com.example.jasstaxi.databinding.ActivityMainBinding
import com.example.jasstaxi.databinding.BottomsheetLayoutBinding
import com.example.jasstaxi.models.BuyurtmaOlish
import com.example.jasstaxi.models.Zakaz
import com.example.jasstaxi.ui.Buyurtma
import com.example.jasstaxi.ui.TaksoMetr
import com.google.firebase.database.*


class SwipeAdapterK(private val context: Buyurtma) :
    RecyclerView.Adapter<SwipeAdapterK.SwipeViewHolder>() {
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
     private lateinit var string: String
     private var buyurtList: ArrayList<BuyurtmaOlish> = ArrayList()
     private lateinit var list: List<String>
     private val viewBinderHelper = ViewBinderHelper()
    private lateinit var progressBar: ProgressBar

    val mediaPlayer1 = MediaPlayer.create(context, R.raw.notification)
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var preferences: SharedPreferences
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeViewHolder {
        val inflate = LayoutInflater.from(context).inflate(R.layout.swipe_layout, parent, false)
        return SwipeViewHolder(inflate)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: SwipeViewHolder, position: Int) {

        sharedPreferences = context.getSharedPreferences("User", 0)

        holder.txt_phone.text = buyurtList[position].numberPhone
        holder.txt_adress.text = buyurtList[position].zakazAdress
        string = buyurtList[position].numberPhone!!

        viewBinderHelper.setOpenOnlyOne(true)
//        viewBinderHelper.bind(holder.swipeRevealLayout.findViewById(R.layout.swipe_layout), buyurtList[position].zakazAdress)
        viewBinderHelper.closeLayout(buyurtList[position].numberPhone)
//        holder.bindData(buyurtList[position])
    }

     override fun getItemCount(): Int {
         return buyurtList.size

     }
     val intent = Intent(context, TaksoMetr::class.java)
     val listItemId: String?
         get() = buyurtList[buyurtList.size - 1].numberPhone


     fun add(newsZakaz: List<BuyurtmaOlish>) {
         val init: Int = buyurtList.size
         buyurtList.clear()
         mediaPlayer1.start()
         notifyDataSetChanged()
         buyurtList.addAll(newsZakaz)
         notifyItemRangeChanged(init, newsZakaz.size)
     }

     inner class SwipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         internal var txt_adress: TextView = itemView.findViewById<TextView>(R.id.buytxt1)
         internal var txt_phone: TextView = itemView.findViewById<TextView>(R.id.phonetxt1)
        val swipeRevealLayout: Button
        val s: String = intent.getStringExtra("a1").toString()

        init {
            binding = ActivityMainBinding.inflate(LayoutInflater.from(context))
            //context.setContentView(binding.root)
            preferences = context.getSharedPreferences("Zakaz",0)
            database = FirebaseDatabase.getInstance()
            swipeRevealLayout = itemView.findViewById(R.id.btn)
            swipeRevealLayout.setOnClickListener { v: View? ->
                intent.putExtra("aaa", buyurtList[position].numberPhone)
                val string: String = sharedPreferences.getString("tel","").toString()
                val joy: String = sharedPreferences.getString("joy","").toString()
                val raqamCar: String = sharedPreferences.getString("avtoraqam", "").toString()
                val tur:String = sharedPreferences.getString("avtotur","").toString()
                val checkUser: Query = FirebaseDatabase.getInstance().getReference("User").orderByChild("phoneNumber").equalTo(string)
                val edit = preferences.edit()

                edit.putString("number",buyurtList[position].numberPhone)
                edit.putString("adress",buyurtList[position].zakazAdress)
                edit.apply()
                checkUser.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val model = Zakaz(buyurtList[position].zakazAdress.toString(),buyurtList[position].numberPhone.toString()
                            ,string, raqamCar, tur)
                            reference = database.getReference("$joy Buyurtmalar:")
                            reference.child(buyurtList[position].numberPhone.toString())
                                .setValue(model)

                            FirebaseDatabase.getInstance().reference
                                .child("Chortoq")
                                .child(buyurtList[position].numberPhone!!)
                                .setValue(null)
                                .addOnCompleteListener { }
                            Toast.makeText(context, string,Toast.LENGTH_LONG).show()
                            context.startActivity(Intent(context,TaksoMetr::class.java))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                Toast.makeText(context,s,Toast.LENGTH_LONG).show()

            }
        }
    }

    }