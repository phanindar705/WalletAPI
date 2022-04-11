package com.phani.WalletAPI.Model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int UserId;

    private String userName;
    private String password;
    private String phoneNumber;
    private String emailId;
    private String active;


}
