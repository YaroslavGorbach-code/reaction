package yaroslavgorbach.reaction.data.statistics.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import java.text.SimpleDateFormat
import java.util.*


@Entity
data class ExerciseStatistics(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: ExerciseName,
    val correctAnswers: Int = 0,
    val numberOfAnswers: Int = 0,
    val averageTimeToAnswer: Long,
    val isSuccess: Boolean,
    val date: Long
) {
    val period: String
        get() {
            val dataFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val firstDay: Date
            val lasDay: Date

            val calendar = Calendar.getInstance(Locale.US)
            calendar.time = Date(date)
            calendar.set(Calendar.DAY_OF_WEEK, 1)
            firstDay = calendar.time
            calendar.add(Calendar.DAY_OF_YEAR, 6)
            lasDay = calendar.time
            return dataFormat.format(firstDay) + " - " + dataFormat.format(lasDay)
        }

    val dayOfWeek: Int
        get() {
            val calendar = Calendar.getInstance(Locale.US)
            calendar.time = Date(date)
            return calendar.get(Calendar.DAY_OF_WEEK)
        }
}