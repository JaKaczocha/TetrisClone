package com.k.logic.events;

public final class MoveEvent {
    private final Type eventType;
    private final Source eventSource;

    public MoveEvent(Type eventType, Source eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    public Type getEventType() {
        return eventType;
    }

    public Source getEventSource() {
        return eventSource;
    }
}
