package com.android.developmentchallenge.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.developmentchallenge.const.DB_VERSION
import com.android.developmentchallenge.model.DailyForecastModel
import com.android.developmentchallenge.room.dao.DailyForecastDAO

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */
@Database(
    entities = [DailyForecastModel::class],
    version = DB_VERSION, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DevChallengeDatabase : RoomDatabase() {
    abstract fun dailyForecastDAO():DailyForecastDAO
}