package io.github.skaiblue.io.github.skaiblue.apns.exception

import java.lang.RuntimeException

/**
 * Exception thrown when an error occurs while loading an APNs key.
 *
 * @author SKAIBlue
 */
class SimpleApnsKeyException(
    message: String
): RuntimeException(message)