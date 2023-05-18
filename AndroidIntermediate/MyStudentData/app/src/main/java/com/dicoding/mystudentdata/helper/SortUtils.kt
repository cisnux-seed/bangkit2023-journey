package com.dicoding.mystudentdata.helper

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    fun getSortedQuery(sortType: SortType): SimpleSQLiteQuery = SimpleSQLiteQuery(
        "SELECT * FROM student ${
            when (sortType) {
                SortType.ASCENDING -> "ORDER BY name ASC"
                SortType.DESCENDING -> "ORDER BY name DESC"
                else -> "ORDER BY RANDOM()"
            }
        }"
    )
}