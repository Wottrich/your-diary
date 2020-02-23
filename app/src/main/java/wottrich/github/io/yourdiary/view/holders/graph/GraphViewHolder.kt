package wottrich.github.io.yourdiary.view.holders.graph


import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.row_profile_graph.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.actualMonth
import wottrich.github.io.yourdiary.extensions.getDateString
import wottrich.github.io.yourdiary.extensions.withoutTime
import wottrich.github.io.yourdiary.model.User
import wottrich.github.io.yourdiary.utils.MyValueFormatter
import java.util.*


class GraphViewHolder(var context: Context, var view: View) : RecyclerView.ViewHolder(view),
    OnChartValueSelectedListener {

    private val viewModel: GraphViewModel by lazy {
        GraphViewModel()
    }

    private lateinit var graph: PieChart

    fun initValues (user: User) {

        val stringFormatMonth = context.getString(R.string.graph_view_holder_info)
        view.tvInfo.text = String.format(stringFormatMonth, Calendar.getInstance().actualMonth())

        viewModel.user = user
        graph = view.gpIncome
        graph.setOnChartValueSelectedListener(this)
        graph.description.isEnabled = false
        graph.description.text = ""
        graph.setTransparentCircleColor(Color.TRANSPARENT)
        graph.setCenterTextColor(Color.TRANSPARENT)
        graph.setHoleColor(Color.TRANSPARENT)
        graph.holeRadius = 80f
        graph.transparentCircleRadius = 0f
        graph.setDrawCenterText(true)

        buildLegend()
        loadChart()
    }

    private fun buildLegend () {
        val legend = graph.legend
        legend.textColor = Color.WHITE
    }

    private fun loadChart () {

        val spending = PieEntry(viewModel.user.allSpendingValue, "Gastos")
        val customer = PieEntry(viewModel.user.allCustomerValue, "Clientes")

        val listEntry = mutableListOf(spending, customer)

        val set = PieDataSet(listEntry, "")
        val colors = intArrayOf(R.color.usc_red, R.color.amazon_green)
        set.colors = ColorTemplate.createColors(context.resources, colors)

        val data = PieData(set)
        data.setValueTextColor(Color.WHITE)
        data.setValueTextSize(14f)
        data.setValueFormatter(MyValueFormatter("R$"))
        graph.data = data
    }

    override fun onValueSelected(entry: Entry?, highlight: Highlight?) {

    }

    override fun onNothingSelected() = Unit

}