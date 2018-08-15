package vn.homecredit.hcvn.service.tracking

import android.content.Context
import javax.inject.Inject

class FBAnalyticsServiceImpl @Inject constructor(context: Context) : AnalyticsService {

    override fun sendEvent(category: String, action: String, label: String) {

    }

    override fun sendView(screen: String) {

    }
}
