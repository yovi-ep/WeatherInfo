package yovi.putra.weatherinfo.view

import android.Manifest
import android.location.Location
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
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

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterHourly: ForecastHourlyAdapter
    private lateinit var adapterDaily: ForecastDailyAdapter

    private val currentWeatherObserver = Observer<CurrentWeather> {
        val dt = DateUtils.parser(it.dt?.toLong()?:0)
        val icon = CommonUtils.getIcon(it.weather[0].main)

        tv_address.text = it.name
        tv_datetime.text = DateUtils.format(dt, Constants.DATE_FORMAT_DEFAULT)
        tv_temperature.text = CommonUtils.getTemperature(this, it.main?.temp)
        tv_weather.text = it.weather[0].main
        img_weather.setImageResource(icon)
        onShowLoading(false, CURRENT_LOADING)
    }

    private val forecastHourlyObserver = Observer<ForecastHourly> {
        adapterHourly.setItem(it.list)
        onShowLoading(false, HOURLY_LOADING)
    }

    private val forecastDailyObserver = Observer<ForecastDaily> {
        adapterDaily.setItem(it.list)
        onShowLoading(false, DAILY_LOADING)
    }

    override fun setupLayout(): Int = R.layout.activity_main

    override fun setupData() {
        adapterHourly = ForecastHourlyAdapter()
        adapterDaily = ForecastDailyAdapter()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.setupView(this@MainActivity)

        getResource()
    }

    private fun getResource() {
        checkPermission(
            mutableListOf(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            getLastLocation()
        }
    }

    private fun getLastLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    task.result?.let {
                        doGetResource(it)
                    }
                }
            }
    }

    private fun doGetResource(it : Location) {
        viewModel.getWeatherCurrent(it.latitude, it.longitude).observe(this, currentWeatherObserver)
        viewModel.getForecastHourly(it.latitude, it.longitude).observe(this, forecastHourlyObserver)
        viewModel.getForecastDaily(it.latitude, it.longitude).observe(this, forecastDailyObserver)
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
            getResource()
            swiperefresh.isRefreshing = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroyPresenter()
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
