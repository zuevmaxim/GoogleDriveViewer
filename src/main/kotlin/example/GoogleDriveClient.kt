package example

import khttp.responses.Response
import org.json.JSONObject

class GoogleDriveClient(private val apiKey : String, private val accessToken : String) {

    fun getAllFiles() : List<String> {
        var response : Response = khttp.get(
            "https://www.googleapis.com/drive/v2/files?",
            params = mapOf("key" to apiKey),
            headers = mapOf("Authorization" to "Bearer $accessToken", "Accept" to "application/json")
        )
        var files = mutableListOf<String>()
        while (true) {
            val obj : JSONObject = response.jsonObject
            files.addAll(obj.getJSONArray("items").toList().map { item -> JSONObject(item)["title"].toString() })
            if (obj["incompleteSearch"] as Boolean) {
                return files;
            }
            response = khttp.get(
                    obj["nextLink"].toString(),
                    params = mapOf("key" to apiKey),
                    headers = mapOf("Authorization" to "Bearer $accessToken", "Accept" to "application/json")
            )
        }
    }
}