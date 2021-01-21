package com.ldhcjs.androidprivatemessenger

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.http.*

interface RetrofitInterface {
    // https://fcm.googleapis.com/v1/projects/myproject-b5ae1/messages:send
    // https://fcm.googleapis.com/fcm/send

//    @Headers(
//        "Content-Type: application/json",
//        "Authorization: key=AAAAHMenarM:APA91bGI0pqsVLM_6M3hK6BXGiUE2QBBHvEkBkW-ZA-tU_COZyGv8Cj9y8W403QclEO5eGJfKIC4ZphLeUsCzAk01tro3xBwI6ofi8uWMMhXw7RP3JmVNJQkIQu1fowrXtqWGo44wIDa"
//    )
    @POST("fcm/send")
    fun postRawJson(
        @HeaderMap headerMap: Map<String, String>, @Body body: JSONObject
    ): Single<ReceivedMsg>
}