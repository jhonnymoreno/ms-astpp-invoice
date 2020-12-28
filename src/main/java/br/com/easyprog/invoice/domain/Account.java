package br.com.easyprog.invoice.domain;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Account {
    private String accountName;
    private String accountNumber;
    private String phoneNumber;
}
