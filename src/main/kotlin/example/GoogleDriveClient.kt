package example

import khttp.responses.Response
import org.json.JSONObject

class GoogleDriveClient(private val apiKey : String, private val accessToken : String) {

    fun getAllFiles() : List<String> {
        val response : Response = khttp.get(
            "https://www.googleapis.com/drive/v2/files?",
            params = mapOf("key" to apiKey),
            headers = mapOf("Authorization" to "Bearer $accessToken", "Accept" to "application/json")
        )
        val obj : JSONObject = response.jsonObject
        return obj.getJSONArray("items")
            .toList()
            .map { item -> (item as JSONObject)["title"].toString() }
    }
}