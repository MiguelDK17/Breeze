package com.migueldk17.breeze.converters


import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsEnum
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import java.util.Collections
import java.util.IdentityHashMap

//Converter Manual de BreezeIconsEnum para String para evitar bugs com o Room e o KSP
fun BreezeIconsEnum.toDatabaseValue(): String {
    return this.name
}

//Converte String para BreezeIconsType
fun String.toBreezeIconsType() : BreezeIconsType {
    return iconByEnumName[this] ?: BreezeIcons.Unspecified.IconUnspecified
}

private val iconByEnumName: Map<String, BreezeIconsType> by lazy {
    val map = mutableMapOf<String, BreezeIconsType>()
    val visited = Collections.newSetFromMap(IdentityHashMap<Any, Boolean>())
    collectIcons(BreezeIcons, map, visited)
    map
}

private fun collectIcons(
    source: Any?,
    map: MutableMap<String, BreezeIconsType>,
    visited: MutableSet<Any>
) {
    if (source == null || !visited.add(source)) return

    val clazz = source.javaClass
    clazz.declaredFields.forEach { field ->
        field.isAccessible = true
        val value = runCatching { field.get(source) }.getOrNull() ?: return@forEach
        when (value) {
            is BreezeIconsType -> map.putIfAbsent(value.enum.name, value)
            else -> {
                val packageName = value.javaClass.`package`?.name.orEmpty()
                if (packageName.startsWith("com.github.migueldk17.breezeicons")) {
                    collectIcons(value, map, visited)
                }
            }
        }
    }
}
