package ru.vlasilya.ledlamp.infrastructure.services.impl

import ru.vlasilya.ledlamp.domain.model.Effect
import ru.vlasilya.ledlamp.infrastructure.repositories.EffectRepository
import ru.vlasilya.ledlamp.infrastructure.services.EffectsService
import ru.vlasilya.ledlamp.infrastructure.services.LampService

class EffectsServiceImpl(
    private val effectRepository: EffectRepository,
) : EffectsService {

    override fun list(): List<Effect> {
        return effectRepository.list()
    }

    override fun update(effect: Effect) {
        effectRepository.updateSettings(effect)
    }
}
