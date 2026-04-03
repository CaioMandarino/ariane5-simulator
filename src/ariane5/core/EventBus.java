package ariane5.core;

import java.util.function.Consumer;

public interface EventBus {
    <T extends DomainEvent> void subscribe(Class<T> eventType, Consumer<T> listener);

    void publish(DomainEvent event);
}
