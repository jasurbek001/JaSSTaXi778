package com.example.jasstaxi

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jasstaxi.databinding.ActivityBuyurtmaBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_buyurtma.*
import kotlinx.android.synthetic.main.list_layout.*
import java.util.concurrent.Executor


class Buyurtma : AppCompatActivity(),IBagLokatsiya {

    val ITEM_COUNT = 10000
    var total_item = 0
    var last_visible_item = 0
    private lateinit var adapter: MyAdapter

    var isLoading = false
    var isMaxDate = false

    var last_node: String = ""
    var last_key: String = ""

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item!!.itemId
        if (id == R.id.refresh) {
            isMaxDate = false
            last_node = adapter.listItemId.toString()
            adapter.notifyDataSetChanged()
            getLastKey()
            getBuyurtma()

        }
        return true
    }
    private var PERMISSION_LOCATION: Int = 0

    lateinit var binding: ActivityBuyurtmaBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyurtmaBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        val actionBar = supportActionBar


        getLastKey()

        val layoutManager = LinearLayoutManager(this)
        buyurt.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(buyurt.context, layoutManager.orientation)
        buyurt.addItemDecoration(dividerItemDecoration)

        adapter = MyAdapter(this)
        buyurt.adapter = adapter

        getBuyurtma()


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

        val stringExtra = intent.getStringExtra("abd")


        actionBar!!.title = stringExtra + "dan buyurtmalar"




    }

    private fun getBuyurtma() {
        if (!isMaxDate) {
            val query: Query
            if (TextUtils.isEmpty(last_node))
                query = FirebaseDatabase.getInstance()
                    .reference.child("Users")
                    .orderByKey().limitToFirst(ITEM_COUNT)
            else
                query = FirebaseDatabase.getInstance()
                    .reference.child("Users")
                    .orderByKey().startAt(last_node).limitToFirst(ITEM_COUNT)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        val buyurList = ArrayList<BuyurtmaOlish>()

                        for (snapShot in snapshot.children)
                            buyurList.add(snapShot.getValue(BuyurtmaOlish::class.java)!!)

                        last_node = buyurList[buyurList.size - 1].numberPhone.toString()

                        adapter.add(buyurList)
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
            .getReference().child("Users")
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
