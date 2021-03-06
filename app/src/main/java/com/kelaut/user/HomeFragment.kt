package com.kelaut.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.kelaut.user.adapter.PromotionAdapter
import com.kelaut.user.model.Promotion
import com.kelaut.user.utils.Constant.Collection.Companion.PROMOTION
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private var promotionList: ArrayList<Promotion> = ArrayList()
    private lateinit var ctx: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        ctx = view

        view.cv_search.setOnClickListener {
            startActivity(Intent(context, SearchActivity::class.java))
        }
        view.cv_booking.setOnClickListener {
            startActivity(Intent(context, BookingActivity::class.java))
        }
        view.cv_news.setOnClickListener {
            startActivity(Intent(context, NewsActivity::class.java))
        }
        view.cv_checklist.setOnClickListener {
            startActivity(Intent(context, CheckListActivity::class.java))
        }

        showPromotionList()

        return view
    }

    private fun showPromotionList() {
        FirebaseFirestore.getInstance().collection(PROMOTION).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val promotion: Promotion = document.toObject(Promotion::class.java)
                    promotion.promotionId = document.id
                    promotionList.add(promotion)
                }
                fillPromotionToLayout()
            }.addOnFailureListener { Log.e("FETCH-PROMOTION", it.message!!) }
    }

    private fun fillPromotionToLayout() {
        val recyclerView: RecyclerView = ctx.findViewById(R.id.rv_promotion)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = PromotionAdapter(context!!, promotionList)
        recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}