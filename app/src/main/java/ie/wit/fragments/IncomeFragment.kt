package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import ie.wit.R
import ie.wit.adapters.FinanceAdapter
import ie.wit.adapters.FinanceListener
import ie.wit.main.FinanceApp
import ie.wit.models.FinanceModel
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_income.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class IncomeFragment : Fragment(), AnkoLogger, FinanceListener {

        lateinit var app: FinanceApp
        lateinit var loader: AlertDialog
        lateinit var root: View

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            app = activity?.application as FinanceApp
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            var root = inflater.inflate(R.layout.fragment_income, container, false)

            root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
            // root.recyclerView.adapter = FinanceAdapter(app.financesStore.findIncome())

            return root
        }

        companion object {
        @JvmStatic
        fun newInstance() =
            IncomeFragment().apply {
                arguments = Bundle().apply { }
            }
    }
//        fun getAllDonations(userId: String?) {
//            showLoader(loader, "Downloading Finances from Firebase")
//            var financeList = ArrayList<FinanceModel>()
//            app.database.child("user-finances").child(userId!!)
//                .addValueEventListener(object : ValueEventListener {
//                    override fun onCancelled(error: DatabaseError) {
//                        info("Firebase Donation error : ${error.message}")
//                    }
//
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        val children = snapshot!!.children
//                        children.forEach {
//                            val finance = it.getValue<FinanceModel>(FinanceModel::class.java!!)
//
//                            financeList.add(finance!!)
//                            app.finances = financeList
//                            root.recyclerView.adapter =
//                                FinanceAdapter(financeList, this@IncomeFragment)
//                            root.recyclerView.adapter?.notifyDataSetChanged()
//                            hideLoader(loader)
//                            app.database.child("user-finances").child(userId!!)
//                                .removeEventListener(this)
//                        }
//                    }
//                })
//        }

    override fun onFinanceClick(finance: FinanceModel) {
        TODO("Not yet implemented")
    }
}

