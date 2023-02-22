package yaroslavgorbach.reaction.utill

import android.app.Activity
import android.app.Application
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import yaroslavgorbach.reaction.data.settings.local.SettingsDataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdManager @Inject constructor(
    private val app: Application,
    private val settingsDataStore: SettingsDataStore
) {

    companion object {
        private const val INTERSTITIAL_AD_FREQUENCY = 2

        private const val REWARD_AD_ID = "ca-app-pub-6043694180023070/6421069001"
        private const val INTERSTITIAL_AD_ID = "ca-app-pub-6043694180023070/5256212403"

        private const val TEST_REWARD_AD_ID = "ca-app-pub-3940256099942544/5224354917"
        private const val TEST_INTERSTITIAL_AD_ID = "ca-app-pub-3940256099942544/1033173712"
    }

    private var rewordAd: RewardedAd? = null
    private var interstitialAd: InterstitialAd? = null
    private val coroutineContext = CoroutineScope(Job() + Dispatchers.IO)

    fun loadRewordAd() {
        val adRequest: AdRequest = AdRequest.Builder().build()

        RewardedAd.load(app, TEST_REWARD_AD_ID,
            adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    rewordAd = null

                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    rewordAd = rewardedAd
                }
            })
    }

    fun loadInterstitialAd() {
        val adRequest: AdRequest = AdRequest.Builder().build()

        InterstitialAd.load(app, TEST_INTERSTITIAL_AD_ID,
            adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitialAd = null

                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }
            })
    }

    fun showInterstitialAd(activity: Activity) {
        val getCounterContext = CoroutineScope(Job() + Dispatchers.IO)

        settingsDataStore.getAddCounter(activity)
            .onEach { counterValue ->
                    if (interstitialAd != null && counterValue >= INTERSTITIAL_AD_FREQUENCY) {
                        interstitialAd!!.fullScreenContentCallback =
                            object : FullScreenContentCallback() {
                                override fun onAdShowedFullScreenContent() {
                                    loadInterstitialAd()
                                    coroutineContext.launch { settingsDataStore.resetAddCounter(activity) }
                                    getCounterContext.cancel()
                                }

                                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                    interstitialAd = null
                                    loadInterstitialAd()
                                }

                                override fun onAdDismissedFullScreenContent() {
                                    interstitialAd = null
                                    loadInterstitialAd()
                                }
                            }

                        withContext(Dispatchers.Main){
                            interstitialAd?.show(activity)
                        }
                    } else {
                        coroutineContext.launch { settingsDataStore.incAddCounter(activity) }
                        getCounterContext.cancel()
                    }
            }.launchIn(getCounterContext)
    }

    fun showRewardAd(activity: Activity, onReword: () -> Unit) {

        if (rewordAd != null) {
            rewordAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdShowedFullScreenContent() {
                    loadRewordAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    rewordAd = null
                    loadRewordAd()
                }

                override fun onAdDismissedFullScreenContent() {
                    rewordAd = null
                    loadRewordAd()
                }
            }

            rewordAd!!.show(activity) { rewardItem ->
                onReword()
            }
        }
    }
}