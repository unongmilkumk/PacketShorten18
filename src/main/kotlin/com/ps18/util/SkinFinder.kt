package com.ps18.util

import com.google.gson.JsonParser
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer
import org.bukkit.entity.Player
import java.io.InputStreamReader
import java.net.URL

object SkinFinder {
    @Suppress("DEPRECATION")
    fun getSkin(player : Player, name : String) : ArrayList<String> {
        try {
            val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")
            val reader = InputStreamReader(url.openStream())

            val uuid = JsonParser.parseReader(reader).asJsonObject.get("id").asString
            val url2 = URL("https://sessionserver.mojang.com/session/minecraft/profile/$uuid?unsigned=false")
            val reader2 = InputStreamReader(url2.openStream())
            val property = JsonParser().parse(reader2).asJsonObject.get("properties").asJsonArray[0].asJsonObject
            val texture = property.get("value").asString
            val signature = property.get("signature").asString
            return arrayListOf(texture, signature)
        } catch (e : Exception) {
            val cp = (player as CraftPlayer)
            val profile = cp.profile
            val property = profile.properties.get("texture").iterator().next()
            val texture = property.value
            val signature = property.signature
            return arrayListOf(texture, signature)
        }
    }
}