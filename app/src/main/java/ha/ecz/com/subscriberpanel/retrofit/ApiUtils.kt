package ha.ecz.com.subscriberpanel.retrofit

object ApiUtils {

    val BASE_URL = "  http://rehajomobileapi.hundredalpha.com/api/"

    val apiService: APIService
        get() = RetrofitClient.getClient(BASE_URL)!!.create(APIService::class.java)
}