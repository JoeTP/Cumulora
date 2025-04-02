package com.example.cumulora.data.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class WeatherRemoteDataSourceImplTest {

    private lateinit var remoteDataSource: WeatherRemoteDataSourceImpl
    private val service = mockk<WeatherService>()

    @Before
    fun setup() {
        remoteDataSource = WeatherRemoteDataSourceImpl(service)
    }

    @Test
    fun getWeather_WithValidCoordinates_ReturnsFlowWithWeatherData() = runTest {
        //! Given
        val expectedWeather = WeatherResponseStub.create()

        coEvery { service.getWeather(any(), any(), any(), any()) } returns (Response.success(expectedWeather))

        //! When
        val result = remoteDataSource.getWeather(
            lat = 40.40,
            lon = 40.40,
            unit = "metric",
            lang = "en"
        )

        //! Then
        result.collect { response ->
            assertEquals("New York", response?.name)
        }
        coVerify { service.getWeather(any() , any(), any(), any()) }
    }

    @Test
    fun getForecast_WithValidCoordinates_AssertingNotNull() = runTest {

        val expectedForecast = ForecastResponseStub.create()
        //! Given
        coEvery { service.getForecast(any(), any(), any(), any()) } returns (Response.success(expectedForecast))

        //! When
        val resultFlow = remoteDataSource.getForecast(
            lat = 40.40,
            lon = 40.40,
            unit = "metric",
            lang = "en"
        )

        //! Then
        resultFlow.collect { response ->
            assertNotNull(response)
        }
        coVerify { service.getForecast(any() , any(), any(), any()) }

    }

}
