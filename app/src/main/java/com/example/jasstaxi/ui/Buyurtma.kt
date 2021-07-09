package com.example.jasstaxi.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jasstaxi.R
import com.example.jasstaxi.models.BuyurtmaOlish
import com.example.jasstaxi.helper.IBagLokatsiya
import com.example.jasstaxi.adapters.SwipeAdapterK
import com.example.jasstaxi.databinding.ActivityBuyurtmaBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.firebase.database.*
import com.google.firebase.database.Query
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_buyurtma.*
import kotlinx.android.synthetic.main.list_layout.*
import kotlin.DeprecationLevel.*


class Buyurtma : AppCompatActivity(), IBagLokatsiya {

    val ITEM_COUNT = 10000
    var total_item = 0
    var last_visible_item = 0
    private lateinit var swipeAdapterK: SwipeAdapterK

    var isLoading = false
    var isMaxDate = false
    private lateinit var db: FirebaseFirestore
    var last_node: String = ""
    var last_key: String = ""
    private var stringExtra: String? = null
    private var PERMISSION_LOCATION: Int = 0
    val buyerList = ArrayList<BuyurtmaOlish>()
    private lateinit var preferences: SharedPreferences
    lateinit var binding: ActivityBuyurtmaBinding

    @SuppressLint("SetTextI18n", "WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyurtmaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = getSharedPreferences("User",0)

        stringExtra = preferences.getString("joy","")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_LOCATION
            )
        } else {
            showLocation()
        }

        intent.putExtra("a1","$stringExtra")

        db = FirebaseFirestore.getInstance()
        getLastKey()

        val layoutManager = LinearLayoutManager(this)
        buyurt.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(buyurt.context, layoutManager.orientation)
        buyurt.addItemDecoration(dividerItemDecoration)

        swipeAdapterK = SwipeAdapterK(this@Buyurtma)
        getBuyurtma()
        buyurt.adapter = swipeAdapterK

        buyurt.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                total_item = layoutManager.itemCount
                last_visible_item = layoutManager.findLastCompletelyVisibleItemPosition()

                if (!isLoading && total_item <= last_visible_item + ITEM_COUNT) {
                    getBuyurtma()
                    isLoading = true
                }
            }
        })
    }



    private fun getBuyurtma() {
        if (!isMaxDate) {
            val query: Query = if (TextUtils.isEmpty(last_node))
                FirebaseDatabase.getInstance()
                    .reference.child("$stringExtra")
                    .orderByKey().limitToFirst(ITEM_COUNT)
            else
                FirebaseDatabase.getInstance()
                    .reference.child("Users")
                    .orderByKey().startAt(last_node).limitToFirst(ITEM_COUNT)

            query.addValueEventListener(object : ValueEventListener {
                @SuppressLint("WrongConstant", "NewApi")
                override fun onDataChange(snapshot: DataSnapshot) {
                    buyerList.clear()
                    if (snapshot.hasChildren()) {
                        for (snapShot in snapshot.children) {
                            buyerList.add(snapShot.getValue(BuyurtmaOlish::class.java)!!)
                        }
                        if (buyerList.isEmpty()) {
                            Toast.makeText(this@Buyurtma,"Hozircha zakazlar yo'q.",Toast.LENGTH_LONG).show()
                        }
                        else {
                            last_node = buyerList[buyerList.size - 1].numberPhone!!
                        }

                        swipeAdapterK.add(buyerList)

                        spin_kit.setColor(1)
                        isLoading = false
                    } else {
                        isLoading = false
                        isMaxDate = true
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Buyurtma, "Tugadi", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun getLastKey() {

        val get_last_key: Query = FirebaseDatabase.getInstance()
            .reference.child("$stringExtra")
            .orderByKey().limitToLast(1)

        get_last_key.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    last_key = userSnapshot.key!!
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    @SuppressLint("MissingPermission")
    private fun showLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,this)

        } else {
            Toast.makeText(this, "Enable GPS...", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    override fun onLocationChanged(location: Location) {}


}
