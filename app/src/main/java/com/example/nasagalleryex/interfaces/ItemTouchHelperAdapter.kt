package com.example.nasagalleryex.interfaces

interface ItemTouchHelperAdapter {

    fun itemMove(fromPos:Int,toPos:Int):Boolean
    fun onItemDismiss(pos:Int)

}