package ru.vlasilya.ledlamp.infrastructure.repositories.impl

import android.content.SharedPreferences
import ru.vlasilya.ledlamp.domain.model.Effect
import ru.vlasilya.ledlamp.domain.utils.effects
import ru.vlasilya.ledlamp.infrastructure.repositories.EffectRepository

class EffectRepositoryImpl(
    private val store: SharedPreferences,
) : EffectRepository {

    val brightKey = { key: Int -> "EFFECT_${key}_BRIGHT" }
    val speedKey = { key: Int -> "EFFECT_${key}_SPEED" }
    val scaleKey = { key: Int -> "EFFECT_${key}_SCALE" }

    override fun list(): List<Effect> {
        return effects.keys.map { key ->
            val bright = store.getInt(brightKey(key), -1)
            val speed = store.getInt(speedKey(key), -1)
            val scale = store.getInt(scaleKey(key), -1)
            val make = effects[key] ?: throw Error()
            make.apply(bright, speed, scale)
        }
    }

    override fun updateSettings(effect: Effect) {
        store.edit()
            .putInt(brightKey(effect.id), effect.bright.value)
            .putInt(speedKey(effect.id),  effect.speed.value)
            .putInt(scaleKey(effect.id),  effect.scale.value)
            .apply()
    }
}
