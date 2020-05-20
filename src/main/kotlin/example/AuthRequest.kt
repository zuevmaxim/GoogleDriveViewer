package example

import khttp.post

class AuthRequest {

    private fun authCodeUrl(codeChallenge: String) = """
        https://accounts.google.com/o/oauth2/v2/auth?
        scope=https://www.googleapis.com/auth/drive.readonly&
        response_type=code&
        state=security_token%3D138r5719ru3e1%26url%3Dhttps%3A%2F%2Foauth2.example.com%2Ftoken&
        redirect_uri=http://localhost&
        client_id=$CLIENT_ID&
        code_challenge=$codeChallenge
    """.trimIndent().split("\n").joinToString("")

    fun runBrowserForAuthCode(challenge: String) {
        val runtime = Runtime.getRuntime()
        runtime.exec("firefox ${authCodeUrl(challenge)}").waitFor()
    }

    fun accessToken(codeChallenge: String, authCode: String): String {
        val response = post(
            "https://oauth2.googleapis.com/token", data = hashMapOf(
                "code" to authCode,
                "client_id" to CLIENT_ID,
                "client_secret" to CLIENT_SECRET,
                "redirect_uri" to "http://localhost",
                "grant_type" to "authorization_code",
                "code_verifier" to codeChallenge
            )
        )
        return response.jsonObject["access_token"] as String
    }

    fun clientId() = CLIENT_ID

    companion object {
        private const val CLIENT_ID = ""
        private const val CLIENT_SECRET = ""
    }
}
