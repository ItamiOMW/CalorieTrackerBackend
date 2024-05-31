package com.itami.utils

import org.mindrot.jbcrypt.BCrypt

object PasswordUtil {

    fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun checkPassword(password: String, hashPassword: String): Boolean {
        return BCrypt.checkpw(password, hashPassword)
    }

}