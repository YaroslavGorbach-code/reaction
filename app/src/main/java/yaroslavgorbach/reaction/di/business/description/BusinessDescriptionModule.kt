package yaroslavgorbach.reaction.di.business.description

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import yaroslavgorbach.reaction.business.description.ObserveDescriptionResInteractor
import yaroslavgorbach.reaction.business.listexercises.ObserveExercisesInteractor
import yaroslavgorbach.reaction.di.business.exericseslist.BusinessExercisesModule

@Module(includes = [BusinessExercisesModule::class])
@InstallIn(ViewModelComponent::class)
class BusinessDescriptionModule {

    @Provides
    fun provideGetDescriptionInteractor(observeExercisesInteractor: ObserveExercisesInteractor): ObserveDescriptionResInteractor {
        return ObserveDescriptionResInteractor(observeExercisesInteractor)
    }
}