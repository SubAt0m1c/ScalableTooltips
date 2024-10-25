package com.github.subat0m1c.scalabletooltips.commands

import com.github.subat0m1c.scalabletooltips.Config
import com.github.subat0m1c.scalabletooltips.Config.update
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText

class tooltipscommand : CommandBase() {
    override fun getCommandName(): String = "tooltips"

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        try {
            args?.get(0)?.lowercase()?.let {
                when (it) {
                    "scale" -> Config.scale.update(
                        try {
                            args[1].toFloatOrNull()?.also { updateConfig("scale", it, sender) } ?: return badArgs(sender)
                        } catch (e: Exception) {
                            return badArgs(sender)
                        }
                    )
                    "toggle" -> {
                        Config.toggled.update(
                            try {
                                args[1].lowercase().toBooleanStrictOrNull()?.also { updateConfig("toggled", it, sender) } ?: return badArgs(sender)
                            } catch (e: Exception) {
                                !Config.toggled.value.also { updateConfig("toggled", !it, sender) }
                            }
                        )
                    }
                    else -> return badArgs(sender)
                }
            } ?: return badArgs(sender)
        } catch (e: Exception) {
            badArgs(sender)
        } finally {
            Config.saveConfig()
        }
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean = true

    override fun getCommandUsage(sender: ICommandSender?): String = "/tooltips <toggle|scale> <value?>"
}

fun updateConfig(type: String, value: Any, sender: ICommandSender?) {
    sender?.addChatMessage(ChatComponentText("Updated $type to $value."))
}

fun badArgs(sender: ICommandSender?) {
    sender?.addChatMessage(ChatComponentText("Invalid arguments! Usage: /tooltips <toggle|scale> <value?>"))
}