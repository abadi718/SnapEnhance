package me.rhunk.snapenhance.action.impl

import me.rhunk.snapenhance.action.AbstractAction
import me.rhunk.snapenhance.config.ConfigProperty
import me.rhunk.snapenhance.features.impl.AutoUpdater

class CheckForUpdates : AbstractAction("action.check_for_updates", dependsOnProperty = ConfigProperty.AUTO_UPDATER) {
    override fun run() {
        context.executeAsync {
            runCatching {
                val latestVersion = context.feature(AutoUpdater::class).checkForUpdates()
                if (latestVersion == null) {
                    context.longToast(context.translation.get("auto_updater.no_update_available"))
                }
            }.onFailure {
                context.longToast(it.message ?: "Failed to check for updates")
            }
        }
    }
}