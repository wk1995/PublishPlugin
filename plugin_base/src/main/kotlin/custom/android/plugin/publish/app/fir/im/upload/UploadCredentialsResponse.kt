package custom.android.plugin.publish.app.fir.im.upload

import kotlinx.serialization.Serializable

/***
 * {
 *   "user_system_default_download_domain": "fir.xcxwo.com",
 *   "id": "677e2b0c23389f7d27073940",
 *   "type": "android",
 *   "short": "uyfbp5ve",
 *   "download_domain": "fir.entertech.cn",
 *   "download_domain_https_ready": true,
 *   "app_user_id": "57a15ca8959d693ba90001c5",
 *   "storage": "qiniu",
 *   "form_method": "POST",
 *   "cert": {
 *     "icon": {
 *       "key": "",
 *       "token": "",
 *       "upload_url": "https://upload.qbox.me",
 *       "custom_headers": {
 *
 *       },
 *       "custom_callback_data": {
 *         "original_key": "417c3b66cab42a37df130d3f267c1dbf063b8eaa"
 *       }
 *     },
 *     "binary": {
 *       "key": "",
 *       "token": "",
 *       "upload_url": "https://upload.qbox.me",
 *       "custom_headers": {
 *
 *       }
 *     },
 *     "mqc": {
 *       "total": 5,
 *       "used": 0,
 *       "is_mqc_availabled": true
 *     },
 *     "support": "qiniu",
 *     "prefix": "x:"
 *   }
 * }
 *
 * */

/**
 * 获取上传凭证响应的数据结构
 * */
@Serializable
data class UploadCredentialsResponse(
    val id: String, val type: String, val short: String, val cert: Cert
)
@Serializable
data class Cert(val icon: CredentialResponse, val binary: CredentialResponse)
@Serializable
data class CredentialResponse(val key:String,val token:String,val upload_url:String)