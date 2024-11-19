package kr.mooner510.telekinesis.utils

import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

object StringUtils {
    fun String.toComponent(): TextComponent {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(this)
    }
}
