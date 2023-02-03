package yaroslavgorbach.reaction.data.statistics.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import java.util.Date

@Entity
data class ExerciseStatistics(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: ExerciseName,
    val correctAnswers: Int = 0,
    val averageTimeToAnswer: Long,
    val isSuccess: Boolean,
    val date: Date
)