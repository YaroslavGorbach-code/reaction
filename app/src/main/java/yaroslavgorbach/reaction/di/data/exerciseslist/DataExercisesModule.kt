package yaroslavgorbach.reaction.di.data.exerciseslist

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yaroslavgorbach.reaction.data.listExercises.repo.RepoExercises
import yaroslavgorbach.reaction.data.listExercises.repo.RepoExercisesImp

@Module
@InstallIn(SingletonComponent::class)
class DataExercisesModule {

    @Provides
    fun provideExercisesRepo(): RepoExercises {
        return RepoExercisesImp()
    }

}