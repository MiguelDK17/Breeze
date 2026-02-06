package com.migueldk17.breeze.converters

import androidx.compose.runtime.Composable
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsEnum
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import java.util.Collections
import java.util.IdentityHashMap

//Converter Manual de BreezeIconsEnum para String para evitar bugs com o Room e o KSP
fun BreezeIconsEnum.toDatabaseValue(): String {
    return this.name
}

private val breezeIconsByEnumName: Map<String, BreezeIconsType> by lazy {
    val iconMap = mutableMapOf<String, BreezeIconsType>()
    val visited = Collections.newSetFromMap(IdentityHashMap<Any, Boolean>())

    fun collectIcons(source: Any?) {
        if (source == null || !visited.add(source)) {
            return
        }

        val sourceClass = source.javaClass
        sourceClass.declaredFields.forEach { field ->
            if (field.name == "INSTANCE") {
                return@forEach
            }
            field.isAccessible = true
            val value = runCatching { field.get(source) }.getOrNull() ?: return@forEach
            when (value) {
                is BreezeIconsType -> iconMap[value.enum.name] = value
                else -> {
                    if (value.javaClass.name.startsWith("com.github.migueldk17.breezeicons")) {
                        collectIcons(value)
                    }
                }
            }
        }
    }

    collectIcons(BreezeIcons)
    iconMap.toMap()
}

//Converte String para BreezeIconsType
@Composable
fun String.toBreezeIconsType() : BreezeIconsType {
    return breezeIconsByEnumName[this] ?: BreezeIcons.Unspecified.IconUnspecified
}
