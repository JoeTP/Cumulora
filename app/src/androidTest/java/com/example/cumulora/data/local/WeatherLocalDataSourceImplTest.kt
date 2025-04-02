package com.example.cumulora.data.local

import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.cumulora.data.local.sharedpref.SharedPreferenceHelper
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.data.models.weather.WeatherEntity
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalTime


@RunWith(AndroidJUnit4::class)
@MediumTest
class WeatherLocalDataSourceImplTest {


    private lateinit var localDataSource: WeatherLocalDataSourceImpl
    private lateinit var database: AppDataBase
    private lateinit var dao: WeatherDao
    private lateinit var sharedPref: SharedPreferenceHelper

    @Before
    fun setup() {
        database = inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = database.weatherDao()
        sharedPref = mockk(relaxed = true)
        localDataSource = WeatherLocalDataSourceImpl(dao, sharedPref)
    }

    @After
    fun tearDown() = database.close()


    @Test
    fun addAlarm_WhenCalled_ShouldStoreAlarm() = runTest {
        //!Given
        val alarm = Alarm(1, "Rain", "Alex", LocalTime.now(), 50)
        //!When
        localDataSource.addAlarm(alarm)
        //!Then
        val result = localDataSource.getAlarms().first()
        assertThat(result.size ,`is`( 1))
    }

    @Test
    fun getAlarmById_WithExistingId_ShouldReturnCorrectAlarm() = runTest {
        val alarm = Alarm(id = 2344, label = "mazim", cityName = "New Diggings", time =LocalTime.now(), duration = 6712)

        //!When
        localDataSource.addAlarm(alarm)
        val result = localDataSource.getAlarmById(2344)
        //!Then
        assertThat(result?.id ,`is`( 2344))

    }

    @Test
    fun deleteAlarm() {

    }

}