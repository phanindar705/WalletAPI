package com.phani.WalletAPI.Model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "UserTransactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;

    private String senderPhoneNumber;
    private String receiverPhoneNumber;
    private double Amount;
    private String transactionStatus;

}
