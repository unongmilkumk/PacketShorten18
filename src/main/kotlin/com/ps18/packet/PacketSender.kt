package com.ps18.packet

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import com.ps18.util.SkinFinder
import net.minecraft.network.protocol.game.*
import net.minecraft.server.level.EntityPlayer
import org.bukkit.Location
import org.bukkit.WeatherType
import org.bukkit.craftbukkit.v1_18_R2.CraftServer
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.util.*

class PacketSender(private var player : Player) {
    private var craftPlayer = player as CraftPlayer
    private var handle = craftPlayer.handle
    private var connection = handle.b
    private var server = (player.server as CraftServer).server
    private var world = (player.world as CraftWorld).handle
    fun destoryEntity(entity : Entity) {
        connection.a(PacketPlayOutEntityDestroy(entity.entityId))
    }
    fun spawnNPC(name : String, skin : String, location: Location) {
        val profile = GameProfile(UUID.randomUUID(), name)
        val s = SkinFinder.getSkin(player, skin)
        profile.properties.put("textures", Property("textures", s[0], s[1]))
        val npc = EntityPlayer(server, world, profile)
        npc.a(location.x, location.y, location.z, location.yaw, location.pitch)
        connection.a(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc))
        connection.a(PacketPlayOutNamedEntitySpawn(npc))
        connection.a(PacketPlayOutEntityHeadRotation(npc, (npc.x * 256 / 360).toInt().toByte()))
    }
    fun setOwnWeather(weather : WeatherType?) {
        if (weather != null) {
            if (weather == WeatherType.DOWNFALL) {
                connection.a(PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.c, 0f))
            } else {
                connection.a(PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.b, 0f))
            }

        } else if (handle.weather == WeatherType.DOWNFALL) {
            connection.a(PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.c, 0f))
        } else {
            connection.a(PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.b, 0f))
        }
    }
    fun setOwnTime(time: Long) {
        connection.a(PacketPlayOutUpdateTime(time, 0L, true))
    }
}