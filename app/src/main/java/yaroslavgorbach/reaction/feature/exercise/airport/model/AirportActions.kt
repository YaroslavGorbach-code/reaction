package yaroslavgorbach.reaction.feature.exercise.airport.model

import android.app.Activity
import yaroslavgorbach.reaction.data.exercise.airport.model.Direction

sealed class AirportActions {
    class Chose(val direction: Direction) : AirportActions()
    object Back : AirportActions()
    object Repeat : AirportActions()
    class FinishExercise(val activity: Activity) : AirportActions()
}