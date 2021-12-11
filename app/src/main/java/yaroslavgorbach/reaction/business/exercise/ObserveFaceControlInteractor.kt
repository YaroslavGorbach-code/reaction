package yaroslavgorbach.reaction.business.exercise

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.exercise.extraNumber.local.model.NumberPack
import yaroslavgorbach.reaction.data.exercise.faceControl.model.FacePack
import yaroslavgorbach.reaction.data.exercise.repo.RepoExercise

class ObserveFaceControlInteractor(private val repoExercise: RepoExercise) {
    operator fun invoke(): Flow<List<FacePack>> {
        return repoExercise.observeFaceControl()
    }
}