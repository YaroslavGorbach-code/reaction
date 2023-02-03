package yaroslavgorbach.reaction.data.statistics.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.data.statistics.model.ExerciseStatistics

@Dao
interface StatisticsDao {

    @Query("SELECT * FROM ExerciseStatistics WHERE name = :exerciseName")
    fun observe(exerciseName: ExerciseName): Flow<List<ExerciseStatistics>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(statistics: List<ExerciseStatistics>)

    @Update
    suspend fun update(statistics: ExerciseStatistics)
}