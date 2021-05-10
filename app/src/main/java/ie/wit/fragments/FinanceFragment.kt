package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import ie.wit.R
import ie.wit.main.FinanceApp
import ie.wit.models.FinanceModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.card_finance.*
import kotlinx.android.synthetic.main.fragment_finance.*
import kotlinx.android.synthetic.main.fragment_finance.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import java.lang.String.format


class FinanceFragment : Fragment(), AnkoLogger {

    lateinit var app: FinanceApp
    var totalFinance = 0
    var totalSpending = 0
    var totalSaved = 0
    lateinit var loader: AlertDialog
    lateinit var eventListener: ValueEventListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as FinanceApp
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_finance, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.action_finance)

        root.amountPicker.minValue = 1
        root.amountPicker.maxValue = 1000
        root.amountPicker.setOnValueChangedListener { _, _, newVal ->

            root.financeAmount.setText("$newVal")

        }
        setButtonListener(root)
        return root;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                FinanceFragment().apply {
                    arguments = Bundle().apply {}
                }
    }

    fun setButtonListener(layout: View) {
        layout.addButton.setOnClickListener {
            val amount = if (layout.financeAmount.text.isNotEmpty())
                layout.financeAmount.text.toString().toInt() else layout.amountPicker.value

            val financemethod = if (layout.financeMethod.checkedRadioButtonId == R.id.Spending) "Spending" else "Income"
            totalFinance += amount

            val financename = financeName.text.toString()
//            app.financesStore.create(FinanceModel(financemethod = financemethod,amount = amount, financename = financename))
            writeNewFinance(FinanceModel(financemethod = financemethod, amount = amount, financename = financename,
                    email = app.auth.currentUser?.email))
        }
    }

    override fun onResume() {
        super.onResume()
        getTotalIncome(app.auth.currentUser?.uid)
        getTotalSpending(app.auth.currentUser?.uid)
        getTotalSaved(app.auth.currentUser?.uid)
    }



    fun writeNewFinance(finance: FinanceModel) {
        showLoader(loader, "Adding Finance to Firebase")
        info("Firebase DB Reference : $app.database")
        val uid = app.auth.currentUser!!.uid
        val key = app.database.child("finances").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        finance.uid = key
        val financeValues = finance.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/finances/$key"] = financeValues
        childUpdates["/user-finances/$uid/$key"] = financeValues

        app.database.updateChildren(childUpdates)
        info(childUpdates)
        hideLoader(loader)
    }

    fun getTotalIncome(userId: String?) {

        eventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                info("Firebase Donation error : ${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var totalIncome = 0

                val children = snapshot!!.children
                children.forEach {

                    val income = it.getValue<FinanceModel>(FinanceModel::class.java!!)
                    if (income!!.financemethod.equals("Income")) {
                        totalIncome += income!!.amount
                    }
                }

                totalIncomeSoFar.text = format("€ $totalIncome")
            }
        }

        app.database.child("user-finances").child(userId!!)
                .addValueEventListener(eventListener)
    }

    fun getTotalSpending(userId: String?) {

        eventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                info("Firebase Donation error : ${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var totalSpending = 0

                val children = snapshot!!.children
                children.forEach {

                    val spending = it.getValue<FinanceModel>(FinanceModel::class.java!!)
                    if (spending?.financemethod.equals("Spending")) {
                        totalSpending += spending!!.amount
                    }
                }

                totalSpendingSoFar.text = format("€ $totalSpending")
            }
        }

        app.database.child("user-finances").child(userId!!)
                .addValueEventListener(eventListener)
    }

    fun getTotalSaved(userId: String?) {

        eventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                info("Firebase Donation error : ${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var totalIncome = 0
                var totalSpending = 0
                var totalSaved = 0
                val children = snapshot!!.children
                children.forEach {

                    val spending = it.getValue<FinanceModel>(FinanceModel::class.java!!)
                    if (spending?.financemethod.equals("Spending")) {
                        totalSpending += spending!!.amount
                    }
                    val income = it.getValue<FinanceModel>(FinanceModel::class.java!!)
                    if (income?.financemethod.equals("Income")) {
                        totalIncome += income!!.amount
                    }
                    totalSaved = totalIncome - totalSpending
                }

                totalSavedSoFar.text = format("€ $totalSaved")
            }
        }

        app.database.child("user-finances").child(userId!!)
                .addValueEventListener(eventListener)
    }


}

