package yaroslavgorbach.reaction.data.exercise.repo

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.exercise.complexSort.model.ComplexSortItem
import yaroslavgorbach.reaction.data.exercise.extraNumber.local.model.NumberPack
import yaroslavgorbach.reaction.data.exercise.extraWord.model.WordPack
import yaroslavgorbach.reaction.data.exercise.faceControl.model.FacePack

interface RepoExercise {
    fun observeExtraNumber(): Flow<List<NumberPack>>

    fun observeExtraWord(): Flow<List<WordPack>>

    fun observeFaceControl(): Flow<List<FacePack>>

    fun observeComplexSort(): Flow<List<ComplexSortItem>>
}