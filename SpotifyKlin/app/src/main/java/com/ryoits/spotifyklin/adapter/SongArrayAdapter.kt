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
import com.ryoits.spotifyklin.model.Song
import com.squareup.picasso.Picasso

class SongArrayAdapter(@NonNull context: Context, @LayoutRes layoutRes: Int = 0, var songList: List<Song>)
    : ArrayAdapter<Song>(context,layoutRes,songList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false)

        val song = songList.get(position)
        itemView?.findViewById<TextView>(R.id.name_artist)?.setText(song.artist_name)
        itemView?.findViewById<TextView>(R.id.name_song)?.setText(song.song_name)
        val imageView = itemView?.findViewById<ImageView>(R.id.imageView)
        Picasso.get().load(song.image).into(imageView)

        return itemView
    }
}