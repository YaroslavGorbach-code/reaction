package yaroslavgorbach.reaction.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import yaroslavgorbach.reaction.data.exercises.local.ExerciseNameConverter
import yaroslavgorbach.reaction.data.exercises.local.dao.ExerciseDao
import yaroslavgorbach.reaction.data.statistics.dao.StatisticsDao
import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.statistics.ExerciseDataConverter
import yaroslavgorbach.reaction.data.statistics.model.ExerciseStatistics

@TypeConverters(ExerciseNameConverter::class, ExerciseDataConverter::class)
@Database(
    entities = [Exercise::class, ExerciseStatistics::class],
    version = 1
)
abstract class ExerciseDatabase : RoomDatabase() {
    companion object {

        @Volatile
        private var INSTANCE: ExerciseDatabase? = null

        fun getInstance(context: Application): ExerciseDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        ExerciseDatabase::class.java,
                        "db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

    abstract val exercise: ExerciseDao
    abstract val statistics: StatisticsDao
}