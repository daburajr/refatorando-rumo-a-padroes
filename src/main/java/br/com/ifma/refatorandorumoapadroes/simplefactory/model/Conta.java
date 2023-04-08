package br.com.ifma.refatorandorumoapadroes.simplefactory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conta {

    Long id;
    Long nossoNumero;
    String digitoVerificadorNossoNumero;
    String carteira;
    String digitoVerificadorConta;
    private BigDecimal saldo;

    public String getCarteira() {
        return Optional.of(this.carteira)
                .orElse("")
                .trim();
    }

}