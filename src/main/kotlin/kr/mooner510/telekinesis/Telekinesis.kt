package kr.mooner510.telekinesis

import dev.rollczi.litecommands.LiteCommands
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory
import kr.mooner510.telekinesis.commands.TelekinesisCommand
import kr.mooner510.telekinesis.utils.StringUtils.toComponent
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockDropItemEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import org.slf4j.LoggerFactory
import java.util.*

class Telekinesis : JavaPlugin(), Listener {
    companion object {
        val telekinesisSet = HashSet<UUID>()
    }

    private val logger = LoggerFactory.getLogger(this::class.java)
    private var liteCommands: LiteCommands<CommandSender>? = null
    private var task: BukkitTask? = null

    private val supportedTools = setOf(
        Material.WOODEN_PICKAXE,
        Material.STONE_PICKAXE,
        Material.IRON_PICKAXE,
        Material.GOLDEN_PICKAXE,
        Material.DIAMOND_PICKAXE,
        Material.NETHERITE_PICKAXE,
        Material.WOODEN_AXE,
        Material.STONE_AXE,
        Material.IRON_AXE,
        Material.GOLDEN_AXE,
        Material.DIAMOND_AXE,
        Material.NETHERITE_AXE,
        Material.WOODEN_SHOVEL,
        Material.STONE_SHOVEL,
        Material.IRON_SHOVEL,
        Material.GOLDEN_SHOVEL,
        Material.DIAMOND_SHOVEL,
        Material.NETHERITE_SHOVEL,
        Material.WOODEN_HOE,
        Material.STONE_HOE,
        Material.IRON_HOE,
        Material.GOLDEN_HOE,
        Material.DIAMOND_HOE,
        Material.NETHERITE_HOE,
        Material.SHEARS,
    )

    @EventHandler
    fun onBreak(e: BlockDropItemEvent) {
        val itemInMainHand = e.player.inventory.itemInMainHand
        if (!supportedTools.contains(itemInMainHand.type)) return
        if (!telekinesisSet.contains(e.player.uniqueId)) return
        e.items.forEach {
            val remain = e.player.inventory.addItem(it.itemStack)
            if (remain.isEmpty()) it.remove()
            else it.itemStack = remain[0]!!
        }
    }

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this)

        liteCommands = LiteBukkitFactory.builder("_telekinesis", this)
            .commands(TelekinesisCommand())
            .build()

        task = Bukkit.getScheduler().runTaskTimer(this, Runnable {
            Bukkit.getOnlinePlayers().forEach {
                if (supportedTools.contains(it.inventory.itemInMainHand.type) && telekinesisSet.contains(it.uniqueId)) {
                    it.sendActionBar("&a[ Tekelinesis Enabled ]".toComponent())
                } else {
                    it.sendActionBar(Component.empty())
                }
            }
        }, 0, 2)

        logger.info("Telekinesis Enabled")
    }

    override fun onDisable() {
        task?.cancel()
        liteCommands?.unregister()
        logger.info("Telekinesis Disabled")
    }
}
