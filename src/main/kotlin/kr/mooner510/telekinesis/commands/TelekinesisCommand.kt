package kr.mooner510.telekinesis.commands

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import org.bukkit.entity.Player

@Command(name = "dev")
@Permission("sbr.dev")
class DevCommand {

    @Execute(name = "gui")
    fun gui(
        @Context sender: Player,
    ) {

    }

}
