package com.itami.utils

import org.apache.commons.mail.*

object EmailManager {

    fun sendPasswordResetCode(
        email: String,
        code: Int
    ) {
        try {
            val smtpUser = System.getenv("SMTP_USER")
            val smtpPassword = System.getenv("SMTP_PASSWORD")
            val emailMessage = HtmlEmail().apply {
                hostName = "smtp.gmail.com"
                setSmtpPort(587)
                setAuthenticator(DefaultAuthenticator(smtpUser, smtpPassword))
                isSSLOnConnect = true
                setFrom(smtpUser)
                subject = "Calorie Tracker - Password reset code."
                setHtmlMsg("<html><body><p>Code: $code</p></body></html>")
                setTextMsg("Code: $code")
                addTo(email)
            }
            emailMessage.send()
            println("Activation email sent successfully")
        } catch (e: EmailException) {
            e.printStackTrace()
        }
    }

    fun sendConfirmationEmail(
        email: String,
        token: String
    ) {
        try {
            val smtpUser = System.getenv("SMTP_USER")
            val smtpPassword = System.getenv("SMTP_PASSWORD")
            val baseUrl = System.getenv("BASE_URL")
            val emailMessage = HtmlEmail().apply {
                hostName = "smtp.gmail.com"
                setSmtpPort(587)
                setAuthenticator(DefaultAuthenticator(smtpUser, smtpPassword))
                isSSLOnConnect = true
                setFrom(smtpUser)
                subject = "Calorie Tracker - Email activation link."
                setHtmlMsg("<html><body><p>Click the following link to activate your account:</p><p><a href='$baseUrl/activate/$token'>Activate Account</a></p></body></html>")
                setTextMsg("Your email client does not support HTML messages. Please visit the following link to activate your account: https://yourdomain.com/activate/$token")
                addTo(email)
            }
            emailMessage.send()
            println("Activation email sent successfully")
        } catch (e: EmailException) {
            e.printStackTrace()
        }
    }

}