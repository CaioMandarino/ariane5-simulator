package ariane5.bus;

import ariane5.core.DomainEvent;
import ariane5.core.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SimpleEventBus implements EventBus {
    private final Map<Class<?>, List<Consumer<?>>> listeners = new HashMap<>();

    @Override
    public <T extends DomainEvent> void subscribe(Class<T> eventType, Consumer<T> listener) {
        listeners.computeIfAbsent(eventType, key -> new ArrayList<>()).add(listener);
    }

    @Override
    public void publish(DomainEvent event) {
        List<Consumer<?>> consumers = listeners.getOrDefault(event.getClass(), List.of());
        for (Consumer<?> consumer : consumers) {
            @SuppressWarnings("unchecked")
            Consumer<DomainEvent> typedConsumer = (Consumer<DomainEvent>) consumer;
            typedConsumer.accept(event);
        }
    }
}
