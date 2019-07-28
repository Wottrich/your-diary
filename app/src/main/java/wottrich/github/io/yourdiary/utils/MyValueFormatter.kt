package wottrich.github.io.yourdiary.utils

import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat


class MyValueFormatter(private val suffix: String) : ValueFormatter() {

    private val mFormat: DecimalFormat = DecimalFormat("###,###,###,##0.00")

    override fun getFormattedValue(value: Float): String {
        return "$suffix ${mFormat.format(value)}"
    }

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return if (axis is XAxis) {
            mFormat.format(value)
        } else if (value > 0) {
            "$suffix ${mFormat.format(value)}"
        } else {
            mFormat.format(value)
        }
    }
}