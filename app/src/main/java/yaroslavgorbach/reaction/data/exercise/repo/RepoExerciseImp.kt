package yaroslavgorbach.reaction.data.exercise.repo

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import yaroslavgorbach.reaction.data.exercise.complexSort.factory.ComplexSortItemsFactory
import yaroslavgorbach.reaction.data.exercise.complexSort.model.ComplexSortItem
import yaroslavgorbach.reaction.data.exercise.extraNumber.local.factory.NumberPacksFactory
import yaroslavgorbach.reaction.data.exercise.extraNumber.local.model.NumberPack
import yaroslavgorbach.reaction.data.exercise.extraWord.factory.WordPacksFactory
import yaroslavgorbach.reaction.data.exercise.extraWord.model.WordPack
import yaroslavgorbach.reaction.data.exercise.faceControl.factory.FacePacksFactory
import yaroslavgorbach.reaction.data.exercise.faceControl.model.FacePack
import yaroslavgorbach.reaction.data.exercise.stroop.factory.StroopWordsFactory
import yaroslavgorbach.reaction.data.exercise.stroop.model.StroopWord

@ExperimentalStdlibApi
class RepoExerciseImp(private val context: Context) : RepoExercise {
    override fun observeExtraNumber(): Flow<List<NumberPack>> {
        return flowOf(NumberPacksFactory().create())
    }

    override fun observeExtraWord(): Flow<List<WordPack>> {
        return flowOf(WordPacksFactory(context).create())
    }

    override fun observeFaceControl(): Flow<List<FacePack>> {
        return flowOf(FacePacksFactory().create())
    }

    override fun observeStroopWords(): Flow<List<StroopWord>> {
        return flowOf(StroopWordsFactory(context).create())
    }

    override fun observeComplexSort(): Flow<List<ComplexSortItem>> {
        return flowOf(ComplexSortItemsFactory().create())
    }

}