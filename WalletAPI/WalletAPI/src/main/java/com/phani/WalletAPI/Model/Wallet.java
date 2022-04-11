package com.phani.WalletAPI.Model;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "UserWallets")
public class Wallet {

    @Id
    private String phoneNumber;

    private double Balance;
}