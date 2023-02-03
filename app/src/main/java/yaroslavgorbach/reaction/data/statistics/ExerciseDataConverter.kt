package yaroslavgorbach.reaction.data.statistics

import androidx.room.TypeConverter
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import java.util.Date

object ExerciseDataConverter {
    @TypeConverter
    fun toDate(value: Long) = Date(value)

    @TypeConverter
    fun fromDate(value: Date) = value.time
}