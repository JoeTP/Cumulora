package com.example.cumulora.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.cumulora.data.models.common.Clouds
import com.example.cumulora.data.models.common.Coord
import com.example.cumulora.data.models.common.Wind
import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.Weather
import com.example.cumulora.data.models.weather.WeatherEntity
import com.example.cumulora.data.models.weather.WeatherResponse
import com.example.cumulora.data.remote.ForecastResponseStub
import com.example.cumulora.data.remote.WeatherResponseStub
import com.example.cumulora.data.remote.WeatherService
import com.example.cumulora.features.savedweather.model.SavedWeather
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response


@RunWith(AndroidJUnit4::class)
class WeatherServiceTest {

    private lateinit var weatherService: WeatherService

    @Before
    fun setup() {
        weatherService = mockk {
            coEvery { getWeather(any(), any(), any(), any()) } returns Response.success(
                WeatherResponseStub.create()
            )

            coEvery { getForecast(any(), any(), any(), any()) } returns Response.success(
                ForecastResponseStub.create()
            )
        }
    }

    @Test
    fun getWeatherTest_ReturnSuccess() = runTest {
        //! Given
        val response = weatherService.getWeather(
            lat = 40.40,
            lon = 40.40,
            unit = "metric",
            lang = "en"
        )

        //! Then
        assertTrue(response.isSuccessful)
        assertEquals("New York", response.body()?.name)
    }

    @Test
    fun getForecastTest_ReturnSuccess() = runTest {
        //! Given
        val response = weatherService.getForecast(
            lat = 40.40,
            lon = 40.40,
            unit = "metric",
            lang = "en"
        )

        //! Then
        assertTrue(response.isSuccessful)
        assertEquals("New York", response.body()?.city?.name)
    }

}

