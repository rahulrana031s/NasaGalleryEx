package com.example.nasagalleryex.utils

import android.graphics.Canvas
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nasagalleryex.interfaces.ItemTouchHelperAdapter
import com.example.nasagalleryex.interfaces.ItemTouchViewHolder

class MyItemTouchHelperCallback(private val adapter: ItemTouchHelperAdapter):ItemTouchHelper.Callback() {

    companion object{
        const val ALPHA_FULL = 1.0f
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return  if(recyclerView.layoutManager is GridLayoutManager){
            val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            val swipeFlag = 0
            makeMovementFlags(dragFlag,swipeFlag)
        }else{
            val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlag = ItemTouchHelper.START or ItemTouchHelper.END
            makeMovementFlags(dragFlag,swipeFlag)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        if(viewHolder.itemViewType != target.itemViewType){
            return false
        }else{
            adapter.itemMove(viewHolder.adapterPosition,target.adapterPosition)
            return true
        }
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            val alpha = ALPHA_FULL - Math.abs(dX) / viewHolder.itemView.width.toFloat()
            viewHolder.itemView.alpha= alpha
            viewHolder.itemView.translationX = dX
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.alpha = ALPHA_FULL
        if(viewHolder is ItemTouchViewHolder){
            val itemViewHolder = viewHolder as ItemTouchViewHolder
            itemViewHolder.onItemClear()
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            if(viewHolder is ItemTouchViewHolder){
                val itemHolder = viewHolder as ItemTouchViewHolder
                itemHolder.onItemSelected()
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }
}