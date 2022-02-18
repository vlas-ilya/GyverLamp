package ru.vlasilya.ledlamp.infrastructure.services.impl

import ru.vlasilya.ledlamp.domain.model.Effect
import ru.vlasilya.ledlamp.domain.model.FavoriteEffect
import ru.vlasilya.ledlamp.infrastructure.repositories.FavoriteEffectsRepository
import ru.vlasilya.ledlamp.infrastructure.services.FavoriteEffectsService

class FavoriteEffectsServiceImpl(
    private val favoriteEffectsRepository: FavoriteEffectsRepository
) : FavoriteEffectsService {
    override fun add(effect: Effect) {
        favoriteEffectsRepository.add(effect)
    }

    override fun list(): List<FavoriteEffect> {
        return favoriteEffectsRepository.list()
    }

    override fun delete(id: Long) {
        favoriteEffectsRepository.delete(id)
    }
}