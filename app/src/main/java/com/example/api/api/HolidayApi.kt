package com.example.api.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HolidayApi {
    @GET("PublicHolidays/{year}/BR")
    suspend fun getHolidays(@Path("year") year: Int): Response<List<Holiday>>
}