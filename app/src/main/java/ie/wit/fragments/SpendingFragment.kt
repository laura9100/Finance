package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import ie.wit.R
import ie.wit.adapters.FinanceAdapter
import ie.wit.main.FinanceApp
import kotlinx.android.synthetic.main.fragment_spending.view.*

class SpendingFragment : Fragment() {

    lateinit var app: FinanceApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as FinanceApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_spending, container, false)

        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        root.recyclerView.adapter = FinanceAdapter(app.financesStore.findSpending())

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SpendingFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}