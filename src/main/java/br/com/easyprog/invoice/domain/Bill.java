package br.com.easyprog.invoice.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Bill {
    private Integer callType;
    private String debit;
    private String totalBillTime;
}
