package com.linktic.challenge.inventory.infrastructure.events;

import com.linktic.challenge.inventory.application.port.out.InventoryEventPublisher;
import com.linktic.challenge.inventory.domain.model.InventoryUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsoleInventoryEventPublisher implements InventoryEventPublisher {

    @Override
    public void publish(InventoryUpdatedEvent event) {
        // Implementación básica: mensaje en consola
        log.info("🎯 EVENTO DE INVENTARIO PUBLICADO: {}", event.toMessage());

        // Aquí podrías agregar en el futuro:
        // - RabbitMQ/Kafka
        // - WebSocket
        // - Notificación a otros servicios
    }
}