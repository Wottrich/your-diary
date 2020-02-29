package wottrich.github.io.yourdiary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_day.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.format
import wottrich.github.io.yourdiary.extensions.getDateString
import wottrich.github.io.yourdiary.model.Day

/**
 * @author Wottrich
 * @author lucas.wottrich@operacao.rcadigital.com.br
 * @since 27/02/20
 *
 * Copyright © 2020 your-diary. All rights reserved.
 *
 */

class IncomeDayAdapter(
    var context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var inflater: LayoutInflater = LayoutInflater.from(context)
    var days: List<Day> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProfitViewHolder(inflater.inflate(R.layout.row_day, parent, false))
    }

    override fun getItemCount(): Int = this.days.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as? ProfitViewHolder)?.let { profitViewHolder ->

            val day = days[profitViewHolder.adapterPosition]

            profitViewHolder.itemView.let { view ->

                val backgroundResource = if (day.isProfit) {
                    R.drawable.shape_row_profit_footer
                } else {
                    R.drawable.shape_row_loss_footer
                }

                val date = day.date.getDateString()

                val contentDescription = if (day.isProfit) {
                    "Seu dia ${day.date.getDateString()} está positivo"
                } else {
                    "Seu dia ${day.date.getDateString()} está negativo"
                }
                
                view.contentDescription = contentDescription

                //Control if total is profit our loss
                view.llFooterContainer.setBackgroundResource(backgroundResource)

                //Date
                view.tvDate.text = date

                //Prices
                view.tvProfit.text = day.profit.format()
                view.tvLoss.text = day.loss.format()

                var total = day.profit - day.loss

                if (total < 0) {
                    total *= -1
                }

                view.tvTotal.text = total.format()

            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        val day = days[position]
        return if (day.isProfit) 1 else 2
    }

    fun update (days: List<Day>) {
        this.days = days
        notifyDataSetChanged()
    }

    class ProfitViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}