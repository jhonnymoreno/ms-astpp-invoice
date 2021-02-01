package br.com.easyprog.invoice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CallTypeEnum {
    RECEIVED_CALL(3, "Received"),
    OUTGOING_CALL(0, "Outgoing");

    private final Integer type;
    private final String description;
}
