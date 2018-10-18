package vn.homecredit.hcvn.service.tracking

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import vn.homecredit.hcvn.R
import vn.homecredit.hcvn.utils.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackingService @Inject
constructor(private val context: Context, private val gaAnalyticsServiceImpl: AnalyticsService,
            private val fbAnalyticsService: AnalyticsService,
            private val internalTrackingService: AnalyticsService) {

    private val mapScreen: HashMap<String, Int> = hashMapOf(
            //Home
            "WelcomeActivity" to R.string.ga_screen_welcome,
            "MoreFragment" to R.string.ga_screen_home_more,
            "ContractFragment" to R.string.ga_screen_home_contract,
            "NotificationsFragment" to R.string.ga_screen_home_notification,
            "SupportFragment" to R.string.ga_screen_home_support,
            //Login
            "LoginActivity" to R.string.ga_screen_login,
            "SignUpActivity" to R.string.ga_screen_signup,
            "ga_screen_login_success" to R.string.ga_screen_login_success,
            "SetPasswordActivity" to R.string.ga_screen_login_setpassword,
            "OtpActivity" to R.string.ga_screen_otp,
            "ForgetPasswordActivity" to R.string.ga_screen_login_forgot,
            //Contract
            "ContractDetailActivity" to R.string.ga_screen_contract_detail,
            "CreditCardDetailActivity" to R.string.ga_screen_creditcard_detail,
            "CreditCardListActivity" to R.string.ga_screen_creditcard_list,
            "TransactionDetailActivity" to R.string.ga_screen_transaction_detail,
            "MasterContractDocActivity" to R.string.ga_screen_mc,
            "MasterContractSignActivity" to R.string.ga_screen_mc_sign,
            "SummaryContractActivity" to R.string.ga_screen_mc_summary,
            "PaymentHistoryActivity" to R.string.ga_screen_contract_payment_history,
            "ScheduleDetailActivity" to R.string.ga_screen_contract_schedule_detail,
            "StatementDetailsActivity" to R.string.ga_screen_contract_statement_display,
            "StatementsActivity" to R.string.ga_screen_contract_statement,
            "TransactionListActivity" to R.string.ga_screen_contract_transaction,
            //Support
            "SupportFragment" to R.string.ga_screen_support_ticket,
            "SupportDoneActivity" to R.string.ga_screen_support_ticket_sent,
            //Setting
            "ChangePassActivity" to R.string.ga_screen_setting_changepass,
            "ProfileActivity" to R.string.ga_screen_setting_profile,
            "PayMapActivity" to R.string.ga_screen_setting_map,
            //Momo
            "WhichContractActivity" to R.string.ga_screen_momo_which_contract,
            "MyContractActivity" to R.string.ga_screen_momo_pay_my_contract,
            "PayOthersActivity" to R.string.ga_screen_momo_pay_other,
            "PaymentMomoActivity" to R.string.ga_screen_momo_repayment,
            "PaymentSummaryActivity" to R.string.ga_screen_momo_summary,
            //Cashloan
            "AclIntroductionAFragment" to R.string.ga_screen_cl_intro1,
            "AclIntroductionBFragment" to R.string.ga_screen_cl_intro2,
            "AclIntroductionCFragment" to R.string.ga_screen_cl_intro3,
            "AclSelectLoanTypeFragment" to R.string.ga_screen_cl_intro4
    )


    private val listServiceImpl = arrayListOf(gaAnalyticsServiceImpl, fbAnalyticsService, internalTrackingService)

    fun sendEvent(category: Int, action: Int, label: Int) {
        sendEvent(context.getString(category), context.getString(action), context.getString(label))
    }

    fun sendEvent(category: String, action: String, label: String?) {
        Log.debug("GA Event $category $action $label")
        listServiceImpl.forEach { it.sendEvent(category, action, label ?: "") }
    }

    fun sendView(screen: String?) {
        Log.debug("GA View $screen")
        listServiceImpl.forEach { it.sendView(screen ?: "") }
    }

    fun sendView(screenResc: Int) {
        sendView(context.getString(screenResc))
    }

    fun sendView(activity: Activity) {
        val screenResc = mapScreen[activity.javaClass.simpleName]
        val screen = if (screenResc == null) activity.javaClass.simpleName else context.getString(screenResc)
        sendView(screen)
    }

    fun sendView(fragment: Fragment) {
        val screenResc = mapScreen[fragment.javaClass.simpleName]
        val screen = if (screenResc == null) fragment.javaClass.simpleName else context.getString(screenResc)
        sendView(screen)
    }

}
