package ariane5.core;

import java.time.LocalDateTime;

public interface DomainEvent {
    LocalDateTime occurredAt();

    String description();
}
