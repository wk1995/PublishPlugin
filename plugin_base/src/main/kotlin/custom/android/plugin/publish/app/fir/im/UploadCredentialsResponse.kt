package custom.android.plugin.publish.app.fir.im

data class Cert(val icon: CredentialResponse, val binary: CredentialResponse)

data class UploadCredentialsResponse(
    val id: String, val type: String, val short: String, val cert: Cert
)