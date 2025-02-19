package io.getstream.chat.android.client.api2

import io.getstream.chat.android.client.api.AuthenticatedApi
import io.getstream.chat.android.client.api2.model.requests.UpdateUsersRequest
import io.getstream.chat.android.client.api2.model.response.UpdateUsersResponse
import io.getstream.chat.android.client.call.RetrofitCall
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

@AuthenticatedApi
@MoshiApi
internal interface UserApi {
    @POST("/users")
    fun updateUsers(
        @Query("api_key") apiKey: String,
        @Query("connection_id") connectionId: String,
        @Body body: UpdateUsersRequest,
    ): RetrofitCall<UpdateUsersResponse>
}
