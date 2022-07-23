package com.android.developmentchallenge.di

import androidx.room.Room
import com.android.developmentchallenge.const.DB_NAME
import com.android.developmentchallenge.room.DevChallengeDatabase
import org.koin.dsl.module

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

/**
 * Data base injection module
 * This dependency inject all module for save data base to room
 */
val databaseModule = module {
    single {
        Room.databaseBuilder(get(), DevChallengeDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration().build()
    }
    single { get<DevChallengeDatabase>().dailyForecastDAO() }
}