package com.example.lab6.Movie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab6.*
import com.example.lab6.json.PopularMovies
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity(0) {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate")

        setupBottomNavigation()

        getMovies()
    }

    fun getMovies(){
        RetrofitService.getMovieApi(MovieApi::class.java)
            .getMovieList(getString(R.string.api_key), "rus").enqueue(
            object : Callback<PopularMovies>{
                override fun onResponse(call: Call<PopularMovies>, response: Response<PopularMovies>) {
                    if (response.isSuccessful){
                        progress_bar.visibility = View.GONE
                        recyclerView.apply {
                            setHasFixedSize(true)
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = MoviesAdapter(
                                response.body()!!.results,
                                this@MainActivity
                            )
                        }
                    }
                }
                override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
        })
    }

}
