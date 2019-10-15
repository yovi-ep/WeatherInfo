package yovi.putra.weatherinfo.view

import android.Manifest
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import yovi.putra.mvvm_databinding_corountines_retrofit.utils.Constants
import yovi.putra.weatherinfo.R
import yovi.putra.weatherinfo.adapters.ForecastDailyAdapter
import yovi.putra.weatherinfo.adapters.ForecastHourlyAdapter
import yovi.putra.weatherinfo.base.BaseActivity
import yovi.putra.weatherinfo.models.CurrentWeather
import yovi.putra.weatherinfo.models.ForecastDaily
import yovi.putra.weatherinfo.models.ForecastHourly
import yovi.putra.weatherinfo.utils.*

class MainActivity : BaseActivity(), MainContract.View {
    companion object {
        const val CURRENT_LOADING = "current"
        const val DAILY_LOADING = "daily"
        const val HOURLY_LOADING = "hourly"
    }

    private lateinit var presenter: MainContract.Presenter
    private lateinit var adapterHourly: ForecastHourlyAdapter
    private lateinit var adapterDaily: ForecastDailyAdapter

    override fun setupLayout(): Int = R.layout.activity_main

    override fun setupData() {
        checkPermission(
            mutableListOf(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_PHONE_STATE
            )
        ) {
            adapterHourly = ForecastHourlyAdapter()
            adapterDaily = ForecastDailyAdapter()
            presenter = MainPresenter(this)

            presenter.getWeatherCurrent(-6.2348516, 106.617299)
            presenter.getForecastHourly(-6.2348516, 106.617299)
            presenter.getForecastDaily(-6.2348516, 106.617299)
        }
    }

    override fun setupUI() {
        rv_hourly.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            overScrollMode = View.OVER_SCROLL_NEVER
            adapter = adapterHourly
        }

        rv_daily.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            overScrollMode = View.OVER_SCROLL_NEVER
            setHasFixedSize(true)
            adapter = adapterDaily
        }

        swiperefresh.setColorSchemeColors(
            ContextCompat.getColor(contextView(),R.color.colorPrimary),
            ContextCompat.getColor(contextView(),R.color.colorAccent),
            ContextCompat.getColor(contextView(),R.color.colorAccentSecondary)
        )

        swiperefresh.setOnRefreshListener {
            setupData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroyPresenter()
    }

    override fun onWeatherCurrent(weather: CurrentWeather) {
        val dt = DateUtils.parser(weather.dt?.toLong()?:0)
        val icon = CommonUtils.getIcon(weather.weather[0].main)

        tv_address.text = weather.name
        tv_datetime.text = DateUtils.format(dt, Constants.DATE_FORMAT_DEFAULT)
        tv_temperature.text = CommonUtils.getTemperature(this, weather.main?.temp)
        tv_weather.text = weather.weather[0].main
        img_weather.setImageResource(icon)
    }

    override fun onForecastDaily(weather: ForecastDaily) {
        adapterDaily.setItem(weather.list)
    }

    override fun onForecastHourly(weather: ForecastHourly) {
        adapterHourly.setItem(weather.list)
    }

    override fun onShowLoading(isShow: Boolean, tag: String?) {
        super.onShowLoading(isShow, tag)
        when (tag) {
            CURRENT_LOADING -> swiperefresh.isRefreshing = isShow
            DAILY_LOADING -> if (isShow) {
                pb_daily.visible()
                rv_daily.invisible()
            } else {
                pb_daily.gone()
                rv_daily.visible()
            }
            HOURLY_LOADING -> if (isShow) {
                pb_hourly.visible()
                rv_hourly.invisible()
            } else {
                pb_hourly.gone()
                rv_hourly.visible()
            }
        }
    }
}
