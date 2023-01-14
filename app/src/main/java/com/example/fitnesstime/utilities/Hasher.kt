package com.example.fitnesstime.utilities

import java.security.MessageDigest
import java.util.*

class Hasher {
    companion object{
        fun hashPassword(password: String, salt: StringBuilder, hash: StringBuilder) {
            // Generate a random salt
            val random = Random()
            val saltBytes = ByteArray(16)
            random.nextBytes(saltBytes)
            salt.append(saltBytes.toHex())

            // Hash the password with the salt
            val digest = MessageDigest.getInstance("SHA-512")
            val passwordBytes = password.toByteArray()
            val saltedPassword = passwordBytes + saltBytes
            val hashBytes = digest.digest(saltedPassword)
            hash.append(hashBytes.toHex())
        }

        private fun ByteArray.toHex(): String {
            return joinToString("") { "%02x".format(it) }
        }

        fun verifyPassword(password: String, salt: String, hash: String): Boolean {
            // Hash the password with the salt
            val digest = MessageDigest.getInstance("SHA-512")
            val passwordBytes = password.toByteArray()
            val saltBytes = salt.toByteArray()
            val saltedPassword = passwordBytes + saltBytes
            val hashBytes = digest.digest(saltedPassword)
            val calculatedHash = hashBytes.toHex()

            // Compare the calculated hash to the stored hash
            return calculatedHash == hash
        }
    }
}