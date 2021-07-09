package com.example.jasstaxi.helper

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MySwipeHelper(
    context: Context,
    private val recyclerView: RecyclerView,
     buttonW: Int
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

   private var buttonList:MutableList<MyButton>?=null
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }
}