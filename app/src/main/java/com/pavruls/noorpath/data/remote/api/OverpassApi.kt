package com.pavruls.noorpath.data.remote.api // আপনার প্যাকেজ নেম অনুযায়ী

import com.pavruls.noorpath.data.remote.dto.OverpassResponse // আপনার DTO এর সঠিক পাথ দিন
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OverpassApi {
    @FormUrlEncoded
    @POST("api/interpreter")
    suspend fun getNearbyMosques(
        @Field("data") query: String
    ): OverpassResponse
}