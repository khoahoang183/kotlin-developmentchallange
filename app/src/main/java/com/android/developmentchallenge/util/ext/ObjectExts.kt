package com.example.developmentchallenge.util.ext

import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.memberFunctions

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

/**
 * Clone object if need,
 * Remember this only support data class
 *
 * @param T : Type of class
 * @param obj : target need clone
 * @return : new object
 */
@Suppress("UNCHECKED_CAST")
fun <T : Any> clone(obj: T): T {
    if (!obj::class.isData) {
        println(obj)
        throw Error("clone is only supported for data classes")
    }

    val copy = obj::class.memberFunctions.first { it.name == "copy" }
    val instanceParam = copy.instanceParameter!!
    return copy.callBy(
        mapOf(
            instanceParam to obj
        )
    ) as T
}
