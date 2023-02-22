package yaroslavgorbach.reaction.feature.exercise.geoSwitching.model

import android.app.Activity
import yaroslavgorbach.reaction.feature.exercise.common.model.YesNoChoseVariations

sealed class GeoSwitchingActions {
    class Chose(val yesNoChose: YesNoChoseVariations) : GeoSwitchingActions()
    object Back : GeoSwitchingActions()
    object Repeat : GeoSwitchingActions()
    class FinishExercise(val activity: Activity) : GeoSwitchingActions()
}