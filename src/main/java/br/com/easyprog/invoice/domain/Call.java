package br.com.easyprog.invoice.domain;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Call {
    private String callDate;
    private String callHour;
    private String callerId;
    private String calledNum;
    private String billTime;
    private String debit;
}
