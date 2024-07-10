package io.github.skaiblue.io.github.skaiblue.apns.details

import io.github.skaiblue.io.github.skaiblue.apns.data.ApnsPushType

/**
 * Interface for APNs push details.
 *
 * @author SKAIBlue
 */
interface SimpleApnsPushDetails {
    val token: String
    val topic: String
    val payload: Any
    val type: ApnsPushType
}