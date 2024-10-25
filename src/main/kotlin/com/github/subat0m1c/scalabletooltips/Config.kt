package com.github.subat0m1c.scalabletooltips

import net.minecraftforge.common.config.Configuration
import java.io.File

data class configOption <T> (var value: T, val set: (T) -> T, val put: (T) -> Unit)

//why does forge need such specific setvalue nonsense? Why are floats and doubles interchangable?
object Config {
    val scale = configOption(1.0f, { config.getFloat("scale", Configuration.CATEGORY_GENERAL, it, 0f, 2f,"Tooltip Scale") }) {
        config.get(Configuration.CATEGORY_GENERAL, "scale", 1.0).setValue(it.toDouble())
    }
    val toggled = configOption(true, { config.getBoolean("toggled", Configuration.CATEGORY_GENERAL, it, "Mod toggle") }) {
        config.get(Configuration.CATEGORY_GENERAL, "toggled", true).setValue(it)
    }

    private lateinit var config: Configuration

    fun init(configFile: File) {
        config = Configuration(configFile)

        try {
            config.load()
            toggled.value = toggled.set(toggled.value)
            scale.value = scale.set(scale.value)
        } catch (e: Exception) {
            System.err.println("Failed to load config file!")
        } finally {
            if (config.hasChanged()) {
                config.save()
            }
        }
    }

    fun saveConfig() {
        if (config.hasChanged()) {
            config.save()
        }
    }

    fun <T> configOption<T>.update(value: T) {
        this.value = value
        this.put(value)
    }
}