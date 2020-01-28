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
import com.ryoits.spotifyklin.adapter.GenreArrayAdapter
import com.ryoits.spotifyklin.model.Genre
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var listView : ListView
    lateinit var arrayAdapter: GenreArrayAdapter
    var url = "http://10.10.17.169:8000/artist/genre"
    lateinit var requestQue : RequestQueue
    var listGenre = mutableListOf<Genre>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById<ListView>(R.id.genre_list)
        arrayAdapter = GenreArrayAdapter(context = this, genreList = listGenre)
        requestQue = Volley.newRequestQueue(this)
        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { parent, view, position, id ->
            startActivity(Intent(this,ArtistActivity::class.java).apply {
                putExtra("id",listGenre[position].id.toString())
            })
        }

        fetchAll()
    }

    fun fetchAll() {
        val genreRequest = StringRequest(Request.Method.GET,url,
            Response.Listener {response -> try {
                val jsonObject = JSONObject(response)
                val data = jsonObject.getJSONArray("data")
                Log.d("DATA", data.toString())

                for (i in 0 until data.length()){
                    val item = data.getJSONObject(i)
                    Log.i("Genre => $i",item.toString())
                    var item2 = Genre(item.getString("id").toInt(), item.getString("name"))
                    arrayAdapter.add(item2)
                }
            } catch (jsonEx : JSONException){
                Log.e("FETCH: FAIL:", jsonEx.message.toString())
            }
            },
            Response.ErrorListener { error: VolleyError? ->
                Log.e("FETCH: FAIL", error?.message.toString())
            })


        requestQue.add(genreRequest)
    }
}
