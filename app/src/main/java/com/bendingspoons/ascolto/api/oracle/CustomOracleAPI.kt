package com.bendingspoons.ascolto.api.oracle

import com.bendingspoons.ascolto.api.oracle.model.FcmTokenRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CustomOracleAPI {
    @POST("notifications/fcm")
    suspend fun fcmNotificationToken(@Body reedem: FcmTokenRequest): Response<ResponseBody>
}