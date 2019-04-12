package wottrich.github.io.yourdiary.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_spending.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.cleanText
import wottrich.github.io.yourdiary.extensions.getString
import wottrich.github.io.yourdiary.model.Spending
import wottrich.github.io.yourdiary.utils.CurrencyUtils
import java.util.*

class SpendingAdapter(
        private var spendingList: List<Spending>,
        private var context: Context,
        private var inflater: LayoutInflater = LayoutInflater.from(context)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater.inflate(R.layout.row_spending, viewGroup, false).also {
            return ItemViewHolder(it)
        }
    }


    override fun getItemCount(): Int = spendingList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as ItemViewHolder) {
            val spending = spendingList[this.adapterPosition]
            this.itemView.let { view ->
                view.tvTitle.text = spending.title
                view.tvPrice.text = CurrencyUtils.formatToLocale(spending.price.toString(), Locale("pt", "BR"))
                view.tvDate.text = spending.date?.getString()
                view.tvDescription.text = spending.description
                view.constDesc.visibility = if (!spending.selected) View.GONE else View.VISIBLE

                view.setOnClickListener {
                    view.constDesc.visibility = if (spending.selected) {
                        spending.selected = false
                        View.GONE
                    } else {
                        spending.selected = true
                        View.VISIBLE
                    }
                }

            }
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
