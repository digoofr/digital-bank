package br.com.digital.bank.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exception {
    private Integer status;
    private String titulo;
}
