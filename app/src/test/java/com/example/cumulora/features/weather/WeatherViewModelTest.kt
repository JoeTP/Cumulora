package com.example.cumulora.features.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherResponse
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.data.responsestate.CombinedStateResponse
import com.example.cumulora.data.responsestate.ForecastStateResponse
import com.example.cumulora.data.responsestate.WeatherStateResponse
import com.example.cumulora.utils.DEFAULT_UNITS
import com.example.cumulora.utils.LANG
import com.example.cumulora.utils.LAST_LAT
import com.example.cumulora.utils.LAST_LON
import com.example.cumulora.utils.UNITS
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class WeatherViewModelTest {


    @get:Rule
    val instantExecutorRule: TestRule = InstantTaskExecutorRule()

    val testDispatcher = StandardTestDispatcher()
    lateinit var repo: WeatherRepository
    lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repo = mockk(relaxed = true)

        coEvery { repo.getWeather(any(), any(), any(), any()) } returns flowOf(mockk(relaxed = true))
        coEvery { repo.getForecast(any(), any(), any(), any()) } returns flowOf(mockk(relaxed = true))
        every { repo.getCachedData<String>(LAST_LAT, any()) } returns "0.0"
        every { repo.getCachedData<String>(LAST_LON, any()) } returns "0.0"
        every { repo.getCachedData<String>(UNITS, any()) } returns DEFAULT_UNITS
        every { repo.getCachedData<String>(LANG, any()) } returns "en"

        viewModel = WeatherViewModel(repo)
    }

    @After
    fun tearDown() = Dispatchers.resetMain()


    @Test
    fun getWeatherAndForecast_WithValidData_ShouldUpdateStateToSuccess() = runTest {

        //! When
        viewModel.getWeatherAndForecast(0.0, 0.0, DEFAULT_UNITS, "en")
        advanceUntilIdle()

        //! Then
        val state = viewModel.combinedState.first()
        assertTrue(state is CombinedStateResponse.Success)

        //! caching
        coVerify(exactly = 1) { repo.cacheHomeWeather(any()) }
        verify(exactly = 1) { repo.cacheData(LAST_LAT, "0.0") }
        verify(exactly = 1) { repo.cacheData(LAST_LON, "0.0") }
    }


    @Test
    fun getWeatherAndForecast_shouldFilterForecastByDate() = runTest {
        //! Given
        val mockWeather: WeatherResponse = mockk(relaxed = true)
        val mockForecast = mockk<ForecastResponse> {
            coEvery { forecastList } returns listOf(
                mockk { coEvery { dtTxt } returns "2023-01-01 12:00" },
                mockk { coEvery { dtTxt } returns "2023-01-01 15:00" },
                mockk { coEvery { dtTxt } returns "2023-01-02 12:00" }
            )
        }

        coEvery { repo.getWeather(any(), any(), any(), any()) } returns flowOf(mockWeather)
        coEvery { repo.getForecast(any(), any(), any(), any()) } returns flowOf(mockForecast)

        coEvery { repo.getCachedData(UNITS, any<String>()) } returns "metric"
        coEvery { repo.getCachedData(LANG, any<String>()) } returns "en"
        coEvery { repo.getCachedData(LAST_LAT, any<String>()) } returns "0.0"
        coEvery { repo.getCachedData(LAST_LON, any<String>()) } returns "0.0"

        //! When
        viewModel.getWeatherAndForecast(0.0, 0.0, "metric", "en")
        advanceUntilIdle()

        //! Then
        assertEquals(2, viewModel.forecastFiveDays.value.size)
    }
}