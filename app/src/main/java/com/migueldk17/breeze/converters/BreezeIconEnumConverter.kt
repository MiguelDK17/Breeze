package com.migueldk17.breeze.converters


import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsEnum
import com.github.migueldk17.breezeicons.icons.BreezeIconsType

private val breezeIconsByEnumName: Map<String, BreezeIconsType> by lazy {
    buildBreezeIconsByEnumName()
}

//Converter Manual de BreezeIconsEnum para String para evitar bugs com o Room e o KSP
fun BreezeIconsEnum.toDatabaseValue(): String {
    return this.name
}

//Converte String para BreezeIconsType
fun String.toBreezeIconsType() : BreezeIconsType {
    return breezeIconsByEnumName[this] ?: BreezeIcons.Unspecified.IconUnspecified
}

private fun buildBreezeIconsByEnumName(): Map<String, BreezeIconsType> {
    val result = mutableMapOf<String, BreezeIconsType>()
    val visited = mutableSetOf<Any>()
    collectBreezeIcons(BreezeIcons, result, visited)
    return result
}

private fun collectBreezeIcons(
    target: Any,
    result: MutableMap<String, BreezeIconsType>,
    visited: MutableSet<Any>
) {
    if (!visited.add(target)) return
    val targetClass = target.javaClass
    targetClass.declaredFields.forEach { field ->
        field.isAccessible = true
        val value = runCatching { field.get(target) }.getOrNull() ?: return@forEach
        handleBreezeIconCandidate(value, result, visited)
    }
    targetClass.methods
        .filter { method ->
            method.parameterCount == 0 && method.declaringClass != Any::class.java
        }
        .forEach { method ->
            val value = runCatching { method.invoke(target) }.getOrNull() ?: return@forEach
            handleBreezeIconCandidate(value, result, visited)
        }
}

private fun handleBreezeIconCandidate(
    value: Any,
    result: MutableMap<String, BreezeIconsType>,
    visited: MutableSet<Any>
) {
    when (value) {
        is BreezeIconsType -> result[value.enum.name] = value
        else -> {
            val valueClass = value.javaClass
            if (valueClass.name.startsWith("com.github.migueldk17.breezeicons")) {
                collectBreezeIcons(value, result, visited)
            }
        }
    }
}
