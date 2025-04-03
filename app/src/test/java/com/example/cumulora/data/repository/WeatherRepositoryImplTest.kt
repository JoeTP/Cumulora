package com.example.cumulora.data.repository

import com.example.cumulora.data.local.WeatherLocalDataSource
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.data.remote.WeatherRemoteDataSource
import com.example.cumulora.features.savedweather.model.SavedWeather
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalTime


class WeatherRepositoryImplTest {

    private lateinit var localDataSource: WeatherLocalDataSource
    private lateinit var remoteDataSource: WeatherRemoteDataSource
    private lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        localDataSource = mockk(relaxed = true)
        remoteDataSource = mockk(relaxed = true)
        repository = WeatherRepositoryImpl(remoteDataSource, localDataSource)

    }

    @Test
    fun saveWeather_and_getSavedWeather_success() = runTest {

        coEvery { localDataSource.saveWeather(any()) } returns Unit
        coEvery { localDataSource.getSavedWeather() } returns mockk(relaxed = true)

        val savedWeather = SavedWeather("Alex", mockk(relaxed = true))

        repository.saveWeather(savedWeather)

        val result = repository.getSavedWeather()

        assertNotNull(result)
        coVerify { localDataSource.saveWeather(savedWeather) }

    }


    @Test
    fun getAlarmsTest_insertingAlarm_thenGetAlarms_success() = runTest {

        coEvery { localDataSource.getAlarms() } returns mockk(relaxed = true)
        coEvery { localDataSource.addAlarm(any()) } returns Unit

        val alarm = Alarm(id = 6951, label = "delicata", cityName = "Walnut Village", time = LocalTime.now(), duration = 1396)
        repository.addAlarm(alarm)

        val result = repository.getAlarms()

        assertNotNull(result)
    }

}