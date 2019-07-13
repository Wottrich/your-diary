package wottrich.github.io.yourdiary.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_order.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.model.Spending

class SpendingAdapter(
        private var spendingList: List<Spending>,
        private var context: Context,
        private var inflater: LayoutInflater = LayoutInflater.from(context)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        inflater.inflate(R.layout.row_order, viewGroup, false).let {
            return ItemViewHolder(it)
        }
    }


    override fun getItemCount(): Int = spendingList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as ItemViewHolder) {
            val spending = spendingList[this.adapterPosition]
            this.itemView.let { view ->
                view.tvTitle.text = spending.title
                view.tvTitle.visibility = if (spending.title == null || spending.title!!.isEmpty()) View.GONE else View.VISIBLE
                view.tvPrice.text = if (spending.price == null) 0.0.format() else spending.price?.format()
                view.tvDate.text = spending.date?.getDateString()
                view.tvDescription.text = spending.description
                view.tvDescription.visibility = if (spending.description == null || spending.description!!.isEmpty()) View.GONE else View.VISIBLE
            }
        }
    }

    fun updateList() {
        spendingList = boxList<Spending>().asReversed()
        notifyDataSetChanged()
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
