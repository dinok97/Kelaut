package com.kelaut.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.kelaut.user.adapter.ChecklistAdapter
import com.kelaut.user.model.Checklist
import com.kelaut.user.utils.Constant.Collection.Companion.CHECKLIST
import kotlinx.android.synthetic.main.activity_news.*

class CheckListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_list)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Perlengkapan Melaut"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView: RecyclerView = findViewById(R.id.rv_checklist)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        loadCheckList(recyclerView)

    }

    private fun loadCheckList(recyclerView: RecyclerView) {
        progress_bar.visibility = View.VISIBLE
        val checkList = ArrayList<Checklist>()

        FirebaseFirestore.getInstance().collection(CHECKLIST).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val c: Checklist = document.toObject(Checklist::class.java)
                    checkList.add(c)
                }
                progress_bar.visibility = View.GONE
                insertDataToRecyclerView(recyclerView, checkList)
            }.addOnFailureListener {
                Log.d("DATA", "data gagal diambil")
            }
    }

    private fun insertDataToRecyclerView(
        recyclerView: RecyclerView,
        checkList: ArrayList<Checklist>
    ) {
        val adapter = ChecklistAdapter(this, checkList)
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