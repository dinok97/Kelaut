package com.kelaut.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kelaut.user.adapter.TransactionAdapter
import com.kelaut.user.model.Transaction
import com.kelaut.user.utils.Constant.Collection.Companion.TRANSACTION
import com.kelaut.user.utils.Constant.Progress.Companion.APPROVED
import com.kelaut.user.utils.Constant.Progress.Companion.SUBMITED
import com.kelaut.user.utils.TransactionSorter

class TransactionFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val SEDANG_DIPROSES = "Sedang Diproses"
    private val RIWAYAT = "Riwayat"
    private val USER_EMAIL = "userEmail"

    private lateinit var progressBar: ProgressBar
    private lateinit var tvNoTransaction: TextView
    private lateinit var recyclerView: RecyclerView
    private var transactionList = ArrayList<Transaction>()
    private var transactionOngoingList = ArrayList<Transaction>()
    private var transactionHistoryList = ArrayList<Transaction>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)

        init(view)

        return view
    }

    private fun init(view: View){
        initObject(view)
        initUI(view)
        initEventUI()
    }

    private fun initObject(view: View){

        val mUser = FirebaseAuth.getInstance().currentUser
        val userEmail = mUser?.email
        getTransactionBundleData(userEmail)

        val spinner = view.findViewById<Spinner>(R.id.spinner_transaction_progress)
        val arrayAdapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.transaction_progress,
            android.R.layout.simple_spinner_item
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = this

    }

    private fun initUI(view: View){

        recyclerView = view.findViewById(R.id.rv_transaction)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        progressBar = view.findViewById(R.id.progress_bar)
        tvNoTransaction = view.findViewById(R.id.tv_no_transaction)
        tvNoTransaction.visibility = View.GONE

    }

    private fun initEventUI(){

    }

    private fun getTransactionBundleData(userEmail: String?) {
        val db = FirebaseFirestore.getInstance()
        db.collection(TRANSACTION).whereEqualTo(USER_EMAIL, userEmail).get()
            .addOnSuccessListener { documents ->
                Log.d("DOCUMENTS", transactionList.toString())
                for (document in documents) {
                    val transaction: Transaction =
                        document.toObject(Transaction::class.java)
                    transaction.transactionId = document.id
                    transactionList.add(transaction)
                }
                progressBar.visibility = View.GONE
                Log.d("TRANDATA", transactionList.toString())
                divideList(transactionList)
            }.addOnFailureListener {
                Log.d("DATA", "data gagal diambil")
            }
    }

    private fun divideList(transactionListAll: ArrayList<Transaction>) {
        for (tran in transactionListAll) {
            if (tran.progress == SUBMITED || tran.progress == APPROVED) {
                transactionOngoingList.add(tran)
            } else {
                transactionHistoryList.add(tran)
            }
        }

        setAdapter(transactionOngoingList)
    }

    private fun setAdapter(tranList: ArrayList<Transaction>) {
        if (tranList.isEmpty()) tvNoTransaction.visibility = View.VISIBLE
        else tvNoTransaction.visibility = View.GONE
        val transactionAdapter = TransactionAdapter(context!!,
            TransactionSorter.sort(tranList))
        recyclerView.adapter = transactionAdapter
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {
        val item = adapterView?.getItemAtPosition(position).toString()

        if (item == SEDANG_DIPROSES) {
            setAdapter(transactionOngoingList)
        }
        if (item == RIWAYAT) {
            setAdapter(transactionHistoryList)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Log.d("onNothingSelected", "nothing to do")
    }

    companion object {
        @JvmStatic
        fun newInstance() = TransactionFragment()
    }
}