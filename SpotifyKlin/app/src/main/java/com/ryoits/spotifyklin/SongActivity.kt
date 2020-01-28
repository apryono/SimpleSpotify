package com.ryoits.spotifyklin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ryoits.spotifyklin.adapter.SongArrayAdapter
import com.ryoits.spotifyklin.model.Song
import org.json.JSONException
import org.json.JSONObject

class SongActivity : AppCompatActivity() {
    lateinit var listView : ListView
    lateinit var arrayAdapter: SongArrayAdapter
    var url = "http://10.10.17.169:8000/artist/song/"
    lateinit var requestQue : RequestQueue
    var songList = mutableListOf<Song>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.song_layout)

        listView = findViewById<ListView>(R.id.song_list)
        arrayAdapter = SongArrayAdapter(context = this, songList= songList)
        requestQue = Volley.newRequestQueue(this)
        listView.adapter = arrayAdapter


        fetchAll()
    }

    fun fetchAll() {
        val songRequest = StringRequest(
            Request.Method.GET,url+this.intent.getStringExtra("id"),
            Response.Listener { response -> try {
                val jsonObject = JSONObject(response)
                val data = jsonObject.getJSONArray("data")
                Log.d("DATA", data.toString())

                for (i in 0 until data.length()){
                    val item = data.getJSONObject(i)
                    Log.i("Song => $i",item.toString())
                    var item2 = Song(item.getString("id").toInt(),
                        item.getString("artist_name"),
                        item.getString("song_name"),
                        item.getString("image"))
                    arrayAdapter.add(item2)
                }
            } catch (jsonEx : JSONException){
                Log.e("FETCH: FAIL:", jsonEx.message.toString())
            }
            },
            Response.ErrorListener { error: VolleyError? ->
                Log.e("FETCH: FAIL", error?.message.toString())
            })


        requestQue.add(songRequest)
    }
}
