package com.phani.WalletAPI.Dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
@Builder
public class Response {

    private HttpStatus httpStatus;
    private String info;
    private String data;

}
