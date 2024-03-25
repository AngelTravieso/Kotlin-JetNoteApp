package com.example.jetnoteapp.utils

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun dateFromTimestamp(timestamp: Long): Date? {
        return Date(timestamp)
    }

    @TypeConverter
    fun timeStampFromDate(date: Date): Long {
        return date.time
    }
}