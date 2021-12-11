package yaroslavgorbach.reaction.di.business.exercise

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import yaroslavgorbach.reaction.business.exercise.ObserveExtraNumbersInteractor
import yaroslavgorbach.reaction.business.exercise.ObserveExtraWordsInteractor
import yaroslavgorbach.reaction.business.exercise.ObserveFaceControlInteractor
import yaroslavgorbach.reaction.data.exercise.repo.RepoExercise
import yaroslavgorbach.reaction.di.data.exercise.DataExerciseModule

@Module(includes = [DataExerciseModule::class])
@InstallIn(ViewModelComponent::class)
class BusinessExerciseModule {

    @Provides
    fun provideExtraNumbersInteractor(repoExercise: RepoExercise): ObserveExtraNumbersInteractor {
        return ObserveExtraNumbersInteractor(repoExercise)
    }

    @Provides
    fun provideExtraWordsInteractor(repoExercise: RepoExercise): ObserveExtraWordsInteractor {
        return ObserveExtraWordsInteractor(repoExercise)
    }

    @Provides
    fun provideFaceControlInteractor(repoExercise: RepoExercise): ObserveFaceControlInteractor {
        return ObserveFaceControlInteractor(repoExercise)
    }
}