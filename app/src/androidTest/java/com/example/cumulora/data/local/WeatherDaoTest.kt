package com.example.cumulora.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.cumulora.data.models.weather.WeatherEntity
import com.example.cumulora.features.savedweather.model.SavedWeather
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class WeatherDaoTest {

    private lateinit var database: AppDataBase
    private lateinit var dao: WeatherDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()
        dao = database.weatherDao()
    }

    @After
    fun closeDatabase() = database.close()


    @Test
    fun insertWeather_WithValidEntries_ShouldSaveToDatabase() = runTest {
        //!Given
        val weatherEntity = mockk<WeatherEntity>(relaxed = true)
        val weather = SavedWeather("LA", weatherEntity)
        val weather2 = SavedWeather("LLA", weatherEntity)

        //!When
        dao.insertWeather(weather)
        dao.insertWeather(weather2)

        //!Then
        val resultList = dao.getWeather().first()
        assertThat(resultList.first().cityName, `is`("LA"))
        assertThat(resultList.size, `is`(2))
    }

    @Test
    fun deleteWeather_AfterInsertion_ShouldRemoveFromDatabase() = runTest {
        //!Given
        val weatherEntity = mockk<WeatherEntity>(relaxed = true)
        val weather = SavedWeather("LA", weatherEntity)

        //!When
        dao.insertWeather(weather)
        dao.deleteWeather(weather)

        //!Then
        val resultList = dao.getWeather().first()
        assertThat(resultList.size, `is`(0))
    }

}