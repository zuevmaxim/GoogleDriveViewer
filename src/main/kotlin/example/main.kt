package example

import kotlin.random.Random

private const val SYMBOLS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm-._~"

fun createCodeChallenge() = (1..50)
    .map { Random.nextInt(SYMBOLS.length) }
    .map { SYMBOLS[it] }
    .joinToString("")

fun main() {
    val codeChallenge = createCodeChallenge()
    val auth = AuthRequest()

    auth.runBrowserForAuthCode(codeChallenge)
    print("Please paste auth code here: ")
    val authCode = readLine()!!

    val token = auth.accessToken(codeChallenge, authCode)
    val client = GoogleDriveClient(auth.clientId(), token)
    val files = client.getAllFiles()
    files.forEach {
        println(it)
    }
}
