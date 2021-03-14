package com.kelaut.user

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

class HomeFragment : Fragment() {

    private var promotionList: ArrayList<Promotion> = ArrayList()
    private lateinit var ctx: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        ctx = view

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