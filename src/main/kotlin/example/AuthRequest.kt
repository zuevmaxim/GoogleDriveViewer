package example

import khttp.async.Companion.post

class AuthRequest {

    private fun authCodeUrl(codeChallenge: String) = """
        https://accounts.google.com/o/oauth2/v2/auth?
        scope=email%20profile&
        response_type=code&
        state=security_token%3D138r5719ru3e1%26url%3Dhttps%3A%2F%2Foauth2.example.com%2Ftoken&
        redirect_uri=urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob&
        client_id=$CLIENT_ID&
        code_challenge=$codeChallenge
    """.trimIndent().split("\n").joinToString("")

    fun runBrowserForAuthCode(challenge: String) {
        val runtime = Runtime.getRuntime()
        runtime.exec("firefox ${authCodeUrl(challenge)}").waitFor()
    }

    fun accessToken(codeChallenge: String, authCode: String, callback: (String) -> Unit) {

    }

    companion object {
        private const val CLIENT_ID = ""
        private const val CLIENT_SECRET = ""
    }
}
