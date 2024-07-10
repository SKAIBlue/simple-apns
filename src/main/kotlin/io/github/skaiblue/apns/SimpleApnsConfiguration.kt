package io.github.skaiblue.io.github.skaiblue.apns

import io.github.skaiblue.io.github.skaiblue.apns.exception.SimpleApnsKeyException
import io.jsonwebtoken.Jwts
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import java.util.stream.Collectors

/**
 * Configuration class for APNs.
 *
 * @param keyPath Path to the p8 key file.
 * @param teamId Team ID.
 * @param keyId Key ID.
 * @param isProduction Whether to use the production server. if install app is app store or test flight, set true.
 * @author SKAIBlue
 */
class SimpleApnsConfiguration (
    private val keyPath: String,
    private val teamId: String,
    private val keyId: String,
    private val isProduction: Boolean
) {
    private var p8der: String? = null
    private var key: PrivateKey? = null
    val jwtToken: String
        get() = generateJwtToken()

    init {
        try {
            this.p8der = Files.readAllLines(Path.of(this.keyPath)).stream()
                .filter { s: String -> !s.contains("----") }
                .collect(Collectors.joining())
            val priPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(p8der))
            key = KeyFactory.getInstance("EC").generatePrivate(priPKCS8)
        } catch (e: IOException) {
            // Key 파일을 읽을 수 없음
            throw SimpleApnsKeyException("Cannot read p8 key file")
        } catch (e: InvalidKeySpecException) {
            // Key 파일이 유효하지 않음
            throw SimpleApnsKeyException("Key file is Invalid")
        } catch (e: NoSuchAlgorithmException) {
            // 알고리즘이 지원되지 않음
            throw SimpleApnsKeyException("Algorithm is not supported")
        }
    }

    /**
     * Make a URL for the APNs server.
     *
     * @param deviceToken Device token.
     */
    fun makeUrl(deviceToken: String): String {
        if (isProduction) return "https://api.push.apple.com/3/device/$deviceToken"
        return "https://api.sandbox.push.apple.com/3/device/$deviceToken"
    }

    /**
     * Generate a JWT token.
     */
    private fun generateJwtToken(): String {
        return Jwts.builder()
            .header()
            .keyId(keyId)
            .and()
            .issuer(teamId)
            .issuedAt(Date())
            .signWith(key, Jwts.SIG.ES256)
            .compact()
    }
}