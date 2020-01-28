package com.ryoits.spotifyklin

import android.content.Intent
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
import com.ryoits.spotifyklin.adapter.ArtistArrayAdapter
import com.ryoits.spotifyklin.model.Artist
import org.json.JSONException
import org.json.JSONObject

class ArtistActivity : AppCompatActivity() {

    lateinit var listView : ListView
    lateinit var arrayAdapter: ArtistArrayAdapter
    var url = "http://10.10.17.169:8000/artist/genre/"
    lateinit var requestQue : RequestQueue
    var artistList = mutableListOf<Artist>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.artist_layout)

        listView = findViewById<ListView>(R.id.artist_list)
        arrayAdapter = ArtistArrayAdapter(context = this, artistList= artistList)
        requestQue = Volley.newRequestQueue(this)
        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { parent, view, position, id ->
            startActivity(Intent(this,SongActivity::class.java).apply {
                putExtra("id",artistList[position].id.toString())
            })
        }


        fetchAll()
    }

    fun fetchAll() {
        val artistRequest = StringRequest(
            Request.Method.GET,url+this.intent.getStringExtra("id"),
            Response.Listener { response -> try {
                val jsonObject = JSONObject(response)
                val data = jsonObject.getJSONArray("data")
                Log.d("DATA", data.toString())

                for (i in 0 until data.length()){
                    val item = data.getJSONObject(i)
                    Log.i("Artist => $i",item.toString())
                    var item2 = Artist(item.getString("id").toInt(),
                        item.getString("name"),
                        item.getString("debut"),
                        item.getString("category"),
                        item.getString("genre"),
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


        requestQue.add(artistRequest)
    }
}
