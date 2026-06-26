package edu.metrostate.ics342.mediatracker.data.model
import kotlinx.serialization.Serializable
@Serializable
data class CreateUserRequest(
    val email: String,
    val password: String,
    val username: String,
    val displayName: String,
    val clientId: String,
    val clientSecret: String
)

@Serializable
data class TokenRequest(
    val grantType: String,
    val email: String? = null,
    val password: String? = null,
    val refreshToken: String? = null,
    val clientId: String,
    val clientSecret: String
)

@Serializable
data class UserDto(
    val id: String? = null,
    val email: String? = null,
    val username: String? = null,
    val displayName: String? = null,
    val bio: String? = null,
    val avatarUrl: String? = null,
    val followerCount: Int? = null,
    val followingCount: Int? = null,
    val trackedCount: Int? = null,
    val isFollowing: Boolean? = null,
    val createdAt: String? = null
)

@Serializable
data class TokenResponse(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val user: UserDto? = null
)

@Serializable
data class CreateUserResponse(
    val id: String,
    val email: String,
    val username: String,
    val displayName: String,
    val bio: String? = null,
    val avatarUrl: String? = null,
    val followerCount: Int,
    val followingCount: Int,
    val trackedCount: Int,
    val isFollowing: Boolean? = null,
    val createdAt: String
)