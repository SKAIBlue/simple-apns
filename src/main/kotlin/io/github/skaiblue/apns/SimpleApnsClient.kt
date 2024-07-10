package io.github.skaiblue.io.github.skaiblue.apns

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.skaiblue.io.github.skaiblue.apns.data.ApnsPushSendResponse
import io.github.skaiblue.io.github.skaiblue.apns.details.SimpleApnsPushDetails
import io.github.skaiblue.io.github.skaiblue.apns.exception.SimpleApnsSendException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

/**
 * APNs client that sends push notifications to the APNs server.
 *
 * Before using this class, you need to create a `SimpleApnsConfiguration` object.
 *
 * @author SKAIBlue
 */
class SimpleApnsClient(private val configuration: SimpleApnsConfiguration) {
    private val client = HttpClient.newHttpClient()
    private val mapper = ObjectMapper()

    /**
     * Send a push notification.
     *
     * @param push The push notification to send.
     */
    fun send(push: SimpleApnsPushDetails): CompletableFuture<Unit> {
        val request = buildRequest(push)

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply {
            if (it.statusCode() == 200) return@thenApply
            val res = mapper.readValue(it.body(), ApnsPushSendResponse::class.java)
            throw SimpleApnsSendException("Fail to send push message status code = ${it.statusCode()}], reason = ${res.reason}")
        }
    }

    /**
     * Build a request to send a push notification.
     */
    private fun buildRequest(push: SimpleApnsPushDetails) = push.let {
        val body = mapper.writeValueAsString(push.payload)

        HttpRequest.newBuilder()
            .uri(URI.create(configuration.makeUrl(push.token)))
            .header("content-type", "application/json; charset=utf-8")
            .header("apns-topic", push.topic)
            .header("apns-push-type", push.type.value)
            .header("authorization", "bearer " + configuration.jwtToken)
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build()
    }

}
