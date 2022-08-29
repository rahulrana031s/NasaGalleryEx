package com.example.nasagalleryex.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nasagalleryex.ImageDetailsActivity
import com.example.nasagalleryex.R
import com.example.nasagalleryex.interfaces.ItemTouchHelperAdapter
import com.example.nasagalleryex.interfaces.OnItemDragListner
import com.example.nasagalleryex.databinding.GridItemBinding
import com.example.nasagalleryex.model.NasaDataItem
import java.util.*
import kotlin.collections.ArrayList

class NasaGridAdapter(
    private val context: Context,
    private val nasaItem: ArrayList<NasaDataItem>,
    param: OnItemDragListner
): RecyclerView.Adapter<NasaGridAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    class ViewHolder (private val itemBinding: GridItemBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(nasaItem: NasaDataItem, context: Context) {
            itemBinding.ivPlaceHolder.load(nasaItem.url) {
                // placeholder image is the image used
                // when our image url fails to load.
                placeholder(R.drawable.ic_launcher_background)
            }
            itemBinding.title.text = nasaItem.title
            itemBinding.tvDate.text = nasaItem.date


            itemBinding.mainHolder.setOnClickListener {
                val intent  = Intent(context, ImageDetailsActivity::class.java)
                intent.apply {
                    putExtra("TITLE", nasaItem.title)
                    putExtra("DATE", nasaItem.date)
                    putExtra("URL", nasaItem.url)
                    putExtra("DESC", nasaItem.explanation)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nasaItem: NasaDataItem = nasaItem[position]
        holder.bind(nasaItem,context)

    }

    override fun getItemCount(): Int {
        return  nasaItem.size
    }

    override fun itemMove(fromPos: Int, toPos: Int):Boolean {
        Collections.swap(nasaItem,fromPos,toPos)
        notifyItemMoved(fromPos,toPos)
        return true
    }

    override fun onItemDismiss(pos: Int) {
        nasaItem.removeAt(pos)
        notifyItemRemoved(pos)

    }


}
