package entertech.android.plugin.dependency

import custom.android.plugin.base.dependency.DependencyType.DEPENDENCY_TYPE_IMPLEMENTATION
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * @param configurationName see [DependencyType]
 * */
fun DependencyHandler.affectiveSdk(configurationName: String = DEPENDENCY_TYPE_IMPLEMENTATION) {
    add(configurationName, EntertechDependencyItem.affectiveSdkOfflineFlowTime)
    add(configurationName, EntertechDependencyItem.affectiveSdkApi)
}


fun DependencyHandler.ble() {
    listOf(
        EntertechDependencyItem.ble_enter_tech,
        EntertechDependencyItem.ble_enter_tech_rx,
    ).forEach {
        add(DEPENDENCY_TYPE_IMPLEMENTATION, it)
    }
}


