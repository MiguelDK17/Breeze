package com.migueldk17.breeze.converters

import androidx.compose.runtime.Composable
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsEnum
import com.github.migueldk17.breezeicons.icons.BreezeIconsType

private const val BREEZE_ICONS_PACKAGE_PREFIX = "com.github.migueldk17.breezeicons"

private val breezeIconsCache: Map<String, BreezeIconsType> by lazy { buildBreezeIconsCache() }

//Converter Manual de BreezeIconsEnum para String para evitar bugs com o Room e o KSP
fun BreezeIconsEnum.toDatabaseValue(): String {
    return this.name
}

//Converte String para BreezeIconsType
@Composable
fun String.toBreezeIconsType() : BreezeIconsType {
    return breezeIconsCache[this] ?: BreezeIcons.Unspecified.IconUnspecified
}

private fun buildBreezeIconsCache(): Map<String, BreezeIconsType> {
    val iconMap = mutableMapOf<String, BreezeIconsType>()
    val visited = mutableSetOf<Int>()

    fun collectIcons(container: Any?) {
        if (container == null) return
        if (container is BreezeIconsType) {
            iconMap.putIfAbsent(container.enum.name, container)
            return
        }
        if (!container.javaClass.name.startsWith(BREEZE_ICONS_PACKAGE_PREFIX)) return

        val identity = System.identityHashCode(container)
        if (!visited.add(identity)) return

        container.javaClass.declaredFields.forEach { field ->
            if (field.name == "INSTANCE") return@forEach
            field.isAccessible = true
            val value = runCatching { field.get(container) }.getOrNull()
            collectIcons(value)
        }

        container.javaClass.declaredMethods
            .filter { method ->
                method.parameterCount == 0 &&
                    method.name.startsWith("get") &&
                    method.returnType.name.startsWith(BREEZE_ICONS_PACKAGE_PREFIX)
            }
            .forEach { method ->
                val value = runCatching { method.invoke(container) }.getOrNull()
                collectIcons(value)
            }

        container.javaClass.declaredClasses.forEach { nestedClass ->
            val instance = runCatching { nestedClass.getField("INSTANCE").get(null) }.getOrNull()
            collectIcons(instance)
        }
    }

    collectIcons(BreezeIcons)
    return iconMap
}
