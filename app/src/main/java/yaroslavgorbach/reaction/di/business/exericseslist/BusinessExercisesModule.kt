package yaroslavgorbach.reaction.di.business.exericseslist

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import yaroslavgorbach.reaction.business.listExercises.ObserveExercisesInteractor
import yaroslavgorbach.reaction.data.listExercises.repo.RepoExercises
import yaroslavgorbach.reaction.di.data.exerciseslist.DataExercisesModule

@Module(includes = [DataExercisesModule::class])
@InstallIn(ViewModelComponent::class)
class BusinessExercisesModule {

    @Provides
    fun provideObserveExercisesInteractor(repoExercises: RepoExercises): ObserveExercisesInteractor {
        return ObserveExercisesInteractor(repoExercises)
    }
}