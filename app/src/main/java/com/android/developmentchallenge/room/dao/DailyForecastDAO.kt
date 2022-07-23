package com.android.developmentchallenge.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.developmentchallenge.model.DailyForecastModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface DailyForecastDAO {
    @get:Query("SELECT * FROM daily_forecast_table")
    val dailyForecastList: Single<List<DailyForecastModel>>

}