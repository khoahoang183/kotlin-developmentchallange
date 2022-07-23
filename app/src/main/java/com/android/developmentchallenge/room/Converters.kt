package com.android.developmentchallenge.room

import android.annotation.SuppressLint
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import androidx.room.TypeConverter
import com.android.developmentchallenge.const.DATE_LOCAL_EFFECT_FORMAT
import com.android.developmentchallenge.model.CityModel
import com.android.developmentchallenge.model.ForecastModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

class Converters {

    @SuppressLint("SimpleDateFormat")
    private var df: DateFormat = SimpleDateFormat(DATE_LOCAL_EFFECT_FORMAT)

    @TypeConverter
    fun convertDateToString(date: Date?): String? {
        return df.format(date)
    }

    @TypeConverter
    fun convertStringToDate(string: String?): Date? {
        return df.parse(string)
    }

    @TypeConverter
    fun convertCityModelToString(model: CityModel?): String? {
        return Gson().toJson(model)
    }

    @TypeConverter
    fun convertStringToCityModel(string: String?): CityModel? {
        return Gson().fromJson(string, CityModel::class.java)
    }

    @TypeConverter
    fun convertListForecastModelToString(listModel: ArrayList<ForecastModel>?): String? {
        return Gson().toJson(listModel)
    }

    @TypeConverter
    fun convertStringToListForecastModel(string: String?): ArrayList<ForecastModel>? {
        val dataType = object : TypeToken<ArrayList<ForecastModel>>() {}.type
        return Gson().fromJson(string, dataType)
    }
}