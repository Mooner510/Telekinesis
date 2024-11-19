package kr.mooner510.telekinesis.commands

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import kr.mooner510.telekinesis.Telekinesis
import kr.mooner510.telekinesis.utils.StringUtils.toComponent
import org.bukkit.entity.Player

@Command(name = "telekinesis")
class TelekinesisCommand {

    @Execute
    fun telekinesis(
        @Context sender: Player,
    ) {
        if(Telekinesis.telekinesisSet.contains(sender.uniqueId)) {
            Telekinesis.telekinesisSet.remove(sender.uniqueId)
            sender.sendMessage("&cTelekinesis Disabled!".toComponent())
        } else {
            Telekinesis.telekinesisSet.add(sender.uniqueId)
            sender.sendMessage("&aTelekinesis Enabled!".toComponent())
        }
    }

}
