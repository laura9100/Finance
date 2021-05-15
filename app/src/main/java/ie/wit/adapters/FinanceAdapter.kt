package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.models.FinanceModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation

import kotlinx.android.synthetic.main.card_finance.view.*

interface FinanceListener {
    fun onFinanceClick(finance: FinanceModel)
}
class FinanceAdapter constructor(private var finances: ArrayList<FinanceModel>,
                                 private val listener: FinanceListener)
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
        holder.bind(finance,listener)
    }
    fun removeAt(position: Int) {
        finances.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun getItemCount(): Int = finances.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(finance: FinanceModel, listener: FinanceListener) {

            itemView.tag = finance
            itemView.amount.text = finance.amount.toString()
            itemView.financemethod.text = finance.financemethod
            itemView.financename.text = finance.financename
            itemView.setOnClickListener { listener.onFinanceClick(finance) }
            if(finance.isfavourite) itemView.imagefavourite.setImageResource(android.R.drawable.star_big_on)

        }

    }
}