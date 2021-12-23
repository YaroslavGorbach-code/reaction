package yaroslavgorbach.reaction.feature.exercise.geoSwitching.model

import yaroslavgorbach.reaction.data.exercise.complexSort.model.ComplexSortItem

sealed class GeoSwitchingActions {
    class Chose(val chose: ChoseVariations) : GeoSwitchingActions()
    object Back : GeoSwitchingActions()
    object Repeat : GeoSwitchingActions()
    object FinishExercise : GeoSwitchingActions()
}