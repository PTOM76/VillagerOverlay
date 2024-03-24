package net.mymai1208.villageroverlay

import net.fabricmc.api.ClientModInitializer
import org.apache.commons.lang3.tuple.Pair
import org.apache.logging.log4j.LogManager
import java.util.UUID

object VillagerOverlay : ClientModInitializer {
    @JvmStatic
    val LOGGER = LogManager.getLogger(VillagerOverlay::class.java)

    @JvmStatic
    val openedVillagers = mutableListOf<UUID>()
    @JvmStatic
    var currentOpenVillager: UUID? = null

    override fun onInitializeClient() {
        openedVillagers.clear()
    }
}