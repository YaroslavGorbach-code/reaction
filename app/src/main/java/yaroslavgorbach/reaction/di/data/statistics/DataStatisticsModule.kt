package yaroslavgorbach.reaction.di.data.statistics

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yaroslavgorbach.reaction.data.ExerciseDatabase
import yaroslavgorbach.reaction.data.statistics.dao.StatisticsDao
import yaroslavgorbach.reaction.data.statistics.repo.RepoStatistics
import yaroslavgorbach.reaction.data.statistics.repo.RepoStatisticsImp

@Module
@InstallIn(SingletonComponent::class)
class DataStatisticsModule {

    @ExperimentalStdlibApi
    @Provides
    fun provideStatisticsRepo(dao: StatisticsDao): RepoStatistics {
        return RepoStatisticsImp(dao)
    }

    @Provides
    fun provideStatisticsDao(db: ExerciseDatabase): StatisticsDao {
        return db.statistics
    }
}