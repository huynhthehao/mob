package vn.homecredit.hcvn.service.tracking

interface AnalyticsService {
    fun sendEvent(category: String, action: String, label: String)
    fun sendView(screen: String)
}
