package vn.homecredit.hcvn.data.repository.mock

import com.google.gson.Gson
import vn.homecredit.hcvn.HCVNApp
import vn.homecredit.hcvn.di.module.AppModule
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Inject

object MockManager {
    private val gson = Gson()

    fun <T> fromAssets(filePath: String, clazz: Class<T>): T {
        try {
            val inputStream = HCVNApp.getContext().assets.open(filePath)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charset.forName("UTF-8"))
            return gson.fromJson(json, clazz)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return clazz.newInstance()
    }
}