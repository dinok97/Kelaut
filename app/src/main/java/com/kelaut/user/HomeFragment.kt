package com.kelaut.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelaut.user.adapter.PromotionAdapter
import com.kelaut.user.model.Promotion

class HomeFragment : Fragment() {

    private lateinit var ctx: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        ctx = view

        fillCityToLayout()

        return view
    }

    private fun fillCityToLayout() {
        val recyclerView: RecyclerView = ctx.findViewById(R.id.rv_promotion)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val promo1 = Promotion(
            "1",
            "Test Promotion1",
            "Desc of promotion 1",
            "https://lelogama.go-jek.com/post_featured_image/voucher-go-jek-gratis-banner.jpg"
        )

        val promo2 = Promotion(
            "1",
            "Test Promotion2",
            "Desc of promotion 1",
            "https://lelogama.go-jek.com/post_featured_image/voucher-go-jek-gratis-banner.jpg"
        )

        val promo3 = Promotion(
            "1",
            "Test Promotion3",
            "Desc of promotion 1",
            "https://lelogama.go-jek.com/post_featured_image/voucher-go-jek-gratis-banner.jpg"
        )

        val promotionList = ArrayList<Promotion>()
        promotionList.add(promo1)
        promotionList.add(promo2)
        promotionList.add(promo3)

        val adapter = PromotionAdapter(context!!, promotionList)
        recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}