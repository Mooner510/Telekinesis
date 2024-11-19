package kr.mooner510.skyblockRemake.utils

import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

object StringUtils {
    private val minorWords = setOf(
        "and",
        "or",
        "the",
        "in",
        "on",
        "at",
        "to",
        "for",
        "a",
        "an",
        "but",
        "of"
    )

    fun String.toTitleCase(anchor: String = " "): String {
        return this.split(anchor)
            .mapIndexed { index, word ->
                if (index == 0 || !minorWords.contains(word.lowercase())) {
                    word[0].uppercase() + word.substring(1).lowercase()
                } else {
                    word.lowercase()
                }
            }
            .joinToString(" ")
    }

    fun String.toComponent(): TextComponent {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(this)
    }
}
