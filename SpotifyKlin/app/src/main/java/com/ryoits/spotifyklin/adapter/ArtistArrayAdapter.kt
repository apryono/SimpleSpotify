package com.ryoits.spotifyklin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import com.ryoits.spotifyklin.R
import com.ryoits.spotifyklin.model.Artist
import com.ryoits.spotifyklin.model.Genre
import com.squareup.picasso.Picasso

class ArtistArrayAdapter(@NonNull context: Context, @LayoutRes layoutRes: Int = 0, var artistList: List<Artist>)
    : ArrayAdapter<Artist>(context,layoutRes,artistList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_artist, parent, false)

        val artist = artistList.get(position)
        itemView?.findViewById<TextView>(R.id.artist_name)?.setText(artist.name)
        itemView?.findViewById<TextView>(R.id.artist_debut)?.setText(artist.debut)
        itemView?.findViewById<TextView>(R.id.artist_category)?.setText(artist.category)
        val imageView = itemView?.findViewById<ImageView>(R.id.imageView)
        Picasso.get().load(artist.image).into(imageView)

        return itemView
    }
}