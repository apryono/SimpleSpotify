package com.ryoits.spotifyklin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import com.ryoits.spotifyklin.R
import com.ryoits.spotifyklin.model.Genre

class GenreArrayAdapter(@NonNull context: Context, @LayoutRes layoutRes: Int = 0,var genreList: List<Genre>)
    : ArrayAdapter<Genre>(context,layoutRes,genreList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_genre, parent, false)

        val genre = genreList.get(position)
        itemView?.findViewById<TextView>(R.id.genreName)?.setText(genre.name)
        return itemView
    }
}