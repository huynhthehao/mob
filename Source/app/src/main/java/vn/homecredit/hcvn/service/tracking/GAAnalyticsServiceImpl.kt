package vn.homecredit.hcvn.service.tracking

import android.content.Context
import android.text.TextUtils

import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker

import javax.inject.Inject
import javax.inject.Singleton

import vn.homecredit.hcvn.R
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper

@Singleton
class GAAnalyticsServiceImpl @Inject constructor(private val context: Context, private val preferencesHelper: PreferencesHelper) : AnalyticsService {
    private val googleAnalytics: GoogleAnalytics = GoogleAnalytics.getInstance(context)
    private val tracker: Tracker?
    private val trackerId: String
        get() = context.getString(R.string.ga_trackingId)
    private val profile = preferencesHelper.profile

    init {
        tracker = newTracker()
    }

    private fun newTracker(): Tracker {
        val trackerLocal = googleAnalytics.newTracker(trackerId)
        trackerLocal?.enableExceptionReporting(true)
        return trackerLocal
    }

    override fun sendEvent(category: String, action: String, label: String) {
        if (tracker == null) {
            return
        }
        if (profile != null) {
            if (TextUtils.isEmpty(profile.userTrackingId)) {
                tracker.set("&uid", profile.userTrackingId)
                tracker.set("&cd1", profile.userTrackingId)
            }
        }
        val eventBuilder = HitBuilders.EventBuilder(category, action)
        eventBuilder.setLabel(label)
        tracker.send(eventBuilder.build())
    }

    override fun sendView(screen: String) {
        if (tracker == null) {
            return
        }
        if (profile != null) {
            if (TextUtils.isEmpty(profile.userTrackingId)) {
                tracker.set("&uid", profile.userTrackingId)
                tracker.set("&cd1", profile.userTrackingId)
            }
        }
        tracker.setScreenName(screen)
        tracker.send(HitBuilders.ScreenViewBuilder().build())

    }


}
