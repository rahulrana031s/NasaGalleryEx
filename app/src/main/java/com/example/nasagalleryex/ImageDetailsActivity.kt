package com.example.nasagalleryex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import coil.load
import com.example.nasagalleryex.databinding.ActivityImageDetailsBinding

class ImageDetailsActivity : AppCompatActivity() {

    lateinit var _binding : ActivityImageDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setView()
        getIntentData()
        setToolBar()
    }

    private fun setToolBar() {
        var actionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setView() {
        _binding = ActivityImageDetailsBinding.inflate(layoutInflater)
        setContentView(_binding.root)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun getIntentData() {
        if(intent.getStringExtra("URL").toString().isNotEmpty()){
            print(intent.getStringArrayExtra("URL"))

            _binding.ivHolderImage.load(intent.getStringExtra("URL").toString()) {
                placeholder(R.drawable.ic_launcher_background)
            }
        }
        _binding.tvTitle.text = intent.getStringExtra("TITLE")
        _binding.tvDate.text = intent.getStringExtra("DATE")
        _binding.tvDesc.text = intent.getStringExtra("DESC")
    }
}