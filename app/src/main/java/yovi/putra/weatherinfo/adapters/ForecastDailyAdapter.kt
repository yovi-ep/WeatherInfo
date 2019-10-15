package yovi.putra.weatherinfo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_daily.*
import yovi.putra.weatherinfo.R
import yovi.putra.weatherinfo.models.DailyInfo
import yovi.putra.weatherinfo.utils.CommonUtils
import yovi.putra.weatherinfo.utils.DateUtils

class ForecastDailyAdapter : RecyclerView.Adapter<ForecastDailyAdapter.VHolder>() {
    private val items = mutableListOf<DailyInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder =
        VHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_daily, parent, false)
        )

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: VHolder, position: Int) = holder.binding(items[position])

    fun setItem(daily: MutableList<DailyInfo>) {
        items.clear()
        items.addAll(daily)
        notifyDataSetChanged()
    }

    class VHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun binding(data: DailyInfo) {
            val dt = DateUtils.parser(data.dt?.toLong() ?: 0)
            val icon = CommonUtils.getIcon(data.weather[0].main)
            val temp = CommonUtils.getTemperature(containerView.context, data.temp?.day)

            tv_time.text = DateUtils.format(dt, "EEEE")
            tv_temperature.text = temp
            img_weather.setImageResource(icon)

            containerView.setOnClickListener {  }
        }
    }
}