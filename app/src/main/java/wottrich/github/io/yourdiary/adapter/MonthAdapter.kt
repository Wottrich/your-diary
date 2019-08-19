package wottrich.github.io.yourdiary.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_month.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.DateHelper
import wottrich.github.io.yourdiary.extensions.Month

class MonthAdapter(var context: Context, var onSelectedMonth: (Month) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val months = DateHelper.getAllMonths()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MonthViewHolder(LayoutInflater.from(context).inflate(R.layout.row_month, viewGroup, false))
    }

    override fun getItemCount(): Int = months.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val month = months[holder.adapterPosition]
        with(holder as MonthViewHolder) {
            this.itemView.tvMonthName.text = context.getString(month.nameMonth)
            this.itemView.setOnClickListener { onSelectedMonth(months[holder.adapterPosition]) }
        }
    }

    class MonthViewHolder(var view: View) : RecyclerView.ViewHolder(view)

}