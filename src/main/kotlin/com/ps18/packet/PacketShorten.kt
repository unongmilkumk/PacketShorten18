package com.ps18.packet

import org.bukkit.entity.Player

object PacketShorten {
    fun Player.getPacketSender(): PacketSender {
        return PacketSender(this)
    }
}