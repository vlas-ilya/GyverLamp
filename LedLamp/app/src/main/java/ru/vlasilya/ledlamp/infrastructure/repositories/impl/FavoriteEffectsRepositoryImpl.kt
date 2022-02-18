package ru.vlasilya.ledlamp.infrastructure.repositories.impl

import ru.vlasilya.ledlamp.domain.model.Effect
import ru.vlasilya.ledlamp.domain.model.FavoriteEffect
import ru.vlasilya.ledlamp.domain.utils.effects
import ru.vlasilya.ledlamp.infrastructure.dao.FavoriteEffectsDao
import ru.vlasilya.ledlamp.infrastructure.dao.entities.FavoriteEffectEntity
import ru.vlasilya.ledlamp.infrastructure.repositories.FavoriteEffectsRepository

class FavoriteEffectsRepositoryImpl(
    private val favoriteEffectsDao: FavoriteEffectsDao
) : FavoriteEffectsRepository {
    override fun add(effect: Effect) {
        favoriteEffectsDao.insert(FavoriteEffectEntity(
            0,
            effect.id,
            effect.bright.value,
            effect.speed.value,
            effect.scale.value,
        ))
    }

    override fun list(): List<FavoriteEffect> {
        return favoriteEffectsDao.list().map {
            val make = effects[it.effectId] ?: throw Error()
            FavoriteEffect(
                it.id,
                make.apply(it.bright, it.speed, it.scale)
            )
        }
    }

    override fun delete(id: Long) {
        val entity = favoriteEffectsDao.get(id) ?: return
        favoriteEffectsDao.delete(entity)
    }
}