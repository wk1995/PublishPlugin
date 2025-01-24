package custom.android.plugin.publish.app.net

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets


object HttpUtil {

    @Throws(IOException::class)
    fun sendHttpRequest(
        urlString: String?,
        methodType: RequestMethodType,
        headers: Map<String, String?> = emptyMap(),
        body: String?
    ): String {
        var connection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        val method = methodType.name
        try {
            // 创建 URL 对象
            val url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = method // 设置请求方法
            connection.connectTimeout = 10000 // 设置连接超时时间
            connection.readTimeout = 10000 // 设置读取超时时间

            // 设置请求头
            for ((key, value) in headers) {
                connection.setRequestProperty(key, value)
            }

            // 处理请求体
            if ("POST".equals(method, ignoreCase = true) || "PUT".equals(
                    method, ignoreCase = true
                )
            ) {
                connection.doOutput = true
                if (!body.isNullOrEmpty()) {
                    connection.outputStream.use { outputStream ->
                        outputStream.write(body.toByteArray(StandardCharsets.UTF_8))
                    }
                }
            }

            // 获取响应
            val responseCode = connection.responseCode
            val inputStream =
                if ((responseCode in 200..299)) connection.inputStream else connection.errorStream

            // 读取响应
            reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
            val response = StringBuilder()
            var line: String?
            while ((reader.readLine().also { line = it }) != null) {
                response.append(line)
            }
            return response.toString()
        } finally {
            reader?.close()
            connection?.disconnect()
        }
    }
}