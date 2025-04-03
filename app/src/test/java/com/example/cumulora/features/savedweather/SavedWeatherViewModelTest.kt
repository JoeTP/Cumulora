package com.example.cumulora.features.savedweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cumulora.data.models.weather.WeatherEntity
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.data.responsestate.SavedWeatherStateResponse
import com.example.cumulora.features.savedweather.model.SavedWeather
import com.example.cumulora.utils.DEFAULT_UNITS
import com.example.cumulora.utils.UNITS
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SavedWeatherViewModelTest {

    @get:Rule
    val instantExecutorRule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val repo: WeatherRepository = mockk(relaxed = true)
    private lateinit var viewModel: SavedWeatherViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SavedWeatherViewModel(repo)
    }

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun deleteSavedWeather_insertingWeather_verifyingDeletionCall() = runTest {
        //! Given
        val weather = SavedWeather("Alex", mockk(relaxed = true))
        coEvery { repo.deleteWeather(weather) } returns Unit

        //! When
        viewModel.deleteSavedWeather(weather)

        //! Then
        coVerify { repo.deleteWeather(weather) }
    }

    @Test
    fun restoreDeletedWeatherTest_verifyingAddingCall() = runTest {
        //! Given
        val weatherToRestore = SavedWeather("Alex", mockk(relaxed = true))
        coEvery { repo.saveWeather(weatherToRestore) } returns Unit

        //! When
        viewModel.restoreDeletedWeather(weatherToRestore)

        //! Then
        coVerify { repo.saveWeather(weatherToRestore) }
    }


}