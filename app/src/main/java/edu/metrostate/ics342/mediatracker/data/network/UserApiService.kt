package edu.metrostate.ics342.mediatracker.data.network


import edu.metrostate.ics342.mediatracker.data.model.TokenRequest
import edu.metrostate.ics342.mediatracker.data.model.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("users")
    suspend fun createUser(@Body body: RegisterRequest): Response<Unit>

    /*
    @POST("users")
    suspend fun createUser(@Body request: CreateUserRequest): CreateUserResponse
*/
    @POST("tokens")
    suspend fun login(@Body request: TokenRequest): TokenResponse
}