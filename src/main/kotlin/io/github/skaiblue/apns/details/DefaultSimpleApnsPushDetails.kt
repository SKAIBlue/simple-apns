package io.github.skaiblue.io.github.skaiblue.apns.details

import io.github.skaiblue.io.github.skaiblue.apns.data.ApnsPushType

/**
 * Default implementation of SimpleApnsPushDetails.
 *
 * @author SKAIBlue
 */
class DefaultSimpleApnsPushDetails(
    override val token: String,
    override val topic: String,
    override val payload: Map<String, String>,
    override val type: ApnsPushType = ApnsPushType.ALERT,
): SimpleApnsPushDetails