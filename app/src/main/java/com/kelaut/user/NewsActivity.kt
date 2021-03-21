package com.kelaut.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.kelaut.user.adapter.NewsAdapter
import com.kelaut.user.model.News
import com.kelaut.user.utils.Constant.Collection.Companion.NEWS
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Berita"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val recyclerView: RecyclerView = findViewById(R.id.rv_news)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        loadNewsImage(recyclerView)

    }


    private fun loadNewsImage(recyclerView: RecyclerView){
        progress_bar.visibility = View.VISIBLE
        val newsList = ArrayList<News>()

        db.collection(NEWS).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val news: News = document.toObject(News::class.java)
                    news.newsId = document.id
                    newsList.add(news)
                }
                progress_bar.visibility = View.GONE
                insertDataToRecyclerView(recyclerView, newsList)
            }.addOnFailureListener {
                Log.d("DATA", "data gagal diambil")
            }
    }

    private fun insertDataToRecyclerView(recyclerView: RecyclerView, newsList: ArrayList<News>){
        val adapter = NewsAdapter(this, newsList)
        recyclerView.adapter = adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}