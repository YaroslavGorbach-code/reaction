package yaroslavgorbach.reaction.domain.settings

import kotlinx.coroutines.flow.Flow
import yaroslavgorbach.reaction.data.settings.repo.RepoSettings
import javax.inject.Inject

class ObserveIsFirstAppOpenInteractor @Inject constructor(private val repoSettings: RepoSettings) {
    operator fun invoke(): Flow<Boolean> {
        return repoSettings.observeIsFirstAppOpen()
    }
}