package com.ps18

import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object { lateinit var instance : Main }
    override fun onEnable() {
        instance = this
        logger.info("PacketShorten18 open")
    }

    override fun onDisable() {
        logger.info("PacketShorten18 close")
    }
}