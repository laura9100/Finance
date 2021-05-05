package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import ie.wit.R
import ie.wit.adapters.FinanceAdapter
import ie.wit.adapters.FinanceListener
import ie.wit.main.FinanceApp
import ie.wit.models.FinanceModel
import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_income.view.*
import kotlinx.android.synthetic.main.fragment_spending.view.recyclerView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class SpendingFragment : Fragment(), AnkoLogger, FinanceListener {

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
        root = inflater.inflate(R.layout.fragment_spending, container, false)
        activity?.title="Spending"
        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        // root.recyclerView.adapter = FinanceAdapter(app.financesStore.findIncome())

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = root.recyclerView.adapter as FinanceAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                deleteFinance((viewHolder.itemView.tag as FinanceModel).uid)
                deleteUserFinance(app.auth.currentUser!!.uid,
                        (viewHolder.itemView.tag as FinanceModel).uid)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(root.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onFinanceClick(viewHolder.itemView.tag as FinanceModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(root.recyclerView)



        return root

    }

    companion object {
        @JvmStatic
        fun newInstance() =
                SpendingFragment().apply {
                    arguments = Bundle().apply { }
                }
    }
    fun setSwipeRefresh() {
        root.swiperefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                root.swiperefresh.isRefreshing = true
                getAllFinance(app.auth.currentUser!!.uid)

            }
        })
    }

    fun checkSwipeRefresh() {
        if (root.swiperefresh.isRefreshing) root.swiperefresh.isRefreshing = false
    }
    override fun onResume() {
        super.onResume()
        getAllFinance(app.auth.currentUser!!.uid)
    }


    fun getAllFinance(userId: String?) {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading Finances from Firebase")
        val financeList = ArrayList<FinanceModel>()
        app.database.child("user-finances").child(userId!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Finance error : ${error.message}")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        hideLoader(loader)
                        val children = snapshot.children
                        children.forEach {
                            val finance = it.
                            getValue<FinanceModel>(FinanceModel::class.java)

                            financeList.add(finance!!)
                            root.recyclerView.adapter =
                                    FinanceAdapter(financeList, this@SpendingFragment)
                            root.recyclerView.adapter?.notifyDataSetChanged()
                            checkSwipeRefresh()


                            app.database.child("user-finances").child(userId)
                                    .removeEventListener(this)
                        }
                    }
                })
    }
    fun deleteUserFinance(userId: String, uid: String?) {
        app.database.child("user-finances").child(userId).child(uid!!)
                .addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                snapshot.ref.removeValue()
                            }
                            override fun onCancelled(error: DatabaseError) {
                                info("Firebase Finance error : ${error.message}")
                            }
                        })
    }

    fun deleteFinance(uid: String?) {
        app.database.child("finances").child(uid!!)
                .addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                snapshot.ref.removeValue()
                            }

                            override fun onCancelled(error: DatabaseError) {
                                info("Firebase Finance error : ${error.message}")
                            }
                        })
    }


    override fun onFinanceClick(finance: FinanceModel) {
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.homeFrame, EditFragment.newInstance(finance))
                .addToBackStack(null)
                .commit()
    }
}