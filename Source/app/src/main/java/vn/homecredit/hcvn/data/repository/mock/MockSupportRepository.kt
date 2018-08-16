package vn.homecredit.hcvn.data.repository.mock

import io.reactivex.Single
import vn.homecredit.hcvn.data.model.api.support.SupportHistoryResp
import vn.homecredit.hcvn.data.model.api.support.SupportResp
import vn.homecredit.hcvn.data.remote.RestService
import vn.homecredit.hcvn.data.repository.SupportRepository
import vn.homecredit.hcvn.data.repository.SupportRepositoryImpl
import javax.inject.Inject

class MockSupportRepository @Inject constructor(restService: RestService) : SupportRepositoryImpl(restService) {
    override fun getHistories(): Single<SupportHistoryResp> {
        return Single.just(MockManager.fromAssets("mock/support_histories.json", SupportHistoryResp::class.java))
    }
}