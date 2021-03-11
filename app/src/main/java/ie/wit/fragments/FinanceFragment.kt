package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ie.wit.R
import ie.wit.main.FinanceApp
import ie.wit.models.FinanceModel
import kotlinx.android.synthetic.main.card_finance.*
import kotlinx.android.synthetic.main.fragment_finance.*
import kotlinx.android.synthetic.main.fragment_finance.view.*
import org.jetbrains.anko.toast


class FinanceFragment : Fragment() {

    lateinit var app: FinanceApp
    var totalIncome = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as FinanceApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_finance, container, false)
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

    fun setButtonListener( layout: View) {
        layout.addButton.setOnClickListener {
            val amount = if (layout.financeAmount.text.isNotEmpty())
                layout.financeAmount.text.toString().toInt() else layout.amountPicker.value

                val financemethod = if(layout.financeMethod.checkedRadioButtonId == R.id.Spending) "Spending" else "Income"
                totalIncome += amount
                layout.totalIncomeSoFar.text = "$$totalIncome"

            val financename = financeName.text.toString()
            app.financesStore.create(FinanceModel(financemethod = financemethod,amount = amount, financename = financename))
            }
        }

}

