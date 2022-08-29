package com.example.nasagalleryex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nasagalleryex.adapters.NasaGridAdapter
import com.example.nasagalleryex.utils.MyItemTouchHelperCallback
import com.example.nasagalleryex.interfaces.OnItemDragListner
import com.example.nasagalleryex.databinding.ActivityMainBinding
import com.example.nasagalleryex.model.NasaDataItem
import com.example.nasagalleryex.viewmodels.NasaViewModel
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var _binding : ActivityMainBinding
    lateinit var viewModel: NasaViewModel
    var itemTouchHelper: ItemTouchHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindView()
        setViewModel()
        getData()
        obServerData()
    }

    private fun obServerData() {
        viewModel.getUsers().observe(this, Observer {
            setRecyclerView(it)
        })
    }

    private fun getData() {
        viewModel.getJsonFromAssets()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this)
            .get("Main activity", NasaViewModel::class.java)
    }

    private fun bindView() {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
    }

    private fun setRecyclerView(nasaItem: ArrayList<NasaDataItem>) {
        _binding.rlvImageData.layoutManager = GridLayoutManager(this,2)
        Collections.reverse(nasaItem)
        var adapter = NasaGridAdapter(this, nasaItem,object : OnItemDragListner {
            override fun onStartDragListner(viewHolder: RecyclerView.ViewHolder?) {
                itemTouchHelper!!.startDrag(viewHolder!!)
            }

        })
        _binding.rlvImageData.adapter = adapter
        val callback = MyItemTouchHelperCallback(adapter)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper!!.attachToRecyclerView(_binding.rlvImageData)
    }
}