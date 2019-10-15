package yovi.putra.weatherinfo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_hourly.*
import yovi.putra.weatherinfo.R
import yovi.putra.weatherinfo.models.HourlyInfo
import yovi.putra.weatherinfo.utils.CommonUtils
import yovi.putra.weatherinfo.utils.DateUtils
import java.util.*

class ForecastHourlyAdapter : RecyclerView.Adapter<ForecastHourlyAdapter.VHolder>() {
    private val items = mutableListOf<HourlyInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder =
        VHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hourly, parent, false)
        )

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: VHolder, position: Int) = holder.binding(items[position])

    fun setItem(hourly: MutableList<HourlyInfo>) {
        items.clear()
        items.addAll(hourly)
        notifyDataSetChanged()
    }

    class VHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun binding(data: HourlyInfo) {
            val dt = DateUtils.parser(data.dt?.toLong()?:0)
            tv_time.text = DateUtils.format(dt, "HHaa").toUpperCase(Locale.getDefault())
            tv_temperature.text = CommonUtils.getTemperature(containerView.context, data.main?.temp)
            img_weather.setImageResource(CommonUtils.getIcon(data.weather[0].main))

            containerView.setOnClickListener {  }
        }
    }
}