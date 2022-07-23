package com.android.developmentchallenge.base.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

open class BaseModel  {
    @PrimaryKey
    @SerializedName("id")
    var id: Long? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null

    /**
     * Extent for set id and return this class
     *
     * @param newId : id for config
     * @return : ImageStorageModel
     */
    inline fun <reified T : BaseModel> setIdForCreate(newId: Long): T {
        this.id = newId
        return this as T
    }
}