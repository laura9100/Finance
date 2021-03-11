package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.FinanceModel

import kotlinx.android.synthetic.main.card_finance.view.*

class FinanceAdapter constructor(private var finances: List<FinanceModel>)
    : RecyclerView.Adapter<FinanceAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_finance,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val finance = finances[holder.adapterPosition]
        holder.bind(finance)
    }

    override fun getItemCount(): Int = finances.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(finance: FinanceModel) {
            itemView.amount.text = finance.amount.toString()
            itemView.financemethod.text = finance.financemethod
            itemView.financename.text = finance.financename

        }
    }
}