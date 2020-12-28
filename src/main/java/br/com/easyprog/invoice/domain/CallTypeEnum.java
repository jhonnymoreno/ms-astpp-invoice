package br.com.easyprog.invoice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CallTypeEnum {
    RECEIVED_CALL(3),
    OUTGOING_CALL(0);

    private final Integer type;
}
