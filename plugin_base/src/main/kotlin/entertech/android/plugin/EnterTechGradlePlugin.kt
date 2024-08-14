package entertech.android.plugin

import custom.android.plugin.base.DefaultGradlePlugin
import custom.android.plugin.push.PublishInfoExtension

open class EnterTechGradlePlugin : DefaultGradlePlugin() {

    override fun getPublishInfoExtension(): Class<out PublishInfoExtension> {
        return EnterPublishInfoExtension::class.java
    }
}

