package io.github.skaiblue.io.github.skaiblue.apns.exception

import java.lang.RuntimeException

/**
 * Exception thrown when an error occurs while sending a push notification.
 *
 * @author SKAIBlue
 */
class SimpleApnsSendException(
    message: String
): RuntimeException(message)