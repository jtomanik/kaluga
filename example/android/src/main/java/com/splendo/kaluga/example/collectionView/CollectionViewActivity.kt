package com.splendo.kaluga.example.collectionView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.splendo.kaluga.collectionView.CollectionViewItem
import com.splendo.kaluga.example.R
import kotlinx.android.synthetic.main.activity_collection_view.*

class CollectionViewActivity : AppCompatActivity(R.layout.activity_collection_view) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val items = listOf(
            CollectionViewItem("foo"),
            CollectionViewItem("bar")
        )
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = CollectionViewAdapter(null, items)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}