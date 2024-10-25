package com.github.subat0m1c.scalabletooltips

import com.github.subat0m1c.scalabletooltips.commands.tooltipscommand
import net.minecraft.client.Minecraft
import net.minecraft.init.Blocks
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.io.File

@Mod(modid = "scalabletooltips", useMetadata = true)
class ScalableTooltips {
    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        val configFile = File(event.modConfigurationDirectory, "scalabletooltips.cfg")
        Config.init(configFile)

        ClientCommandHandler.instance.registerCommand(tooltipscommand())
    }
}
