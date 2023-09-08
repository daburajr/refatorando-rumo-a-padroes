package br.com.ifma.refatorandorumoapadroes.simplefactory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conta {

    private Long id;

    private Long nossoNumero;

    private String digitoVerificadorNossoNumero;

    private String carteira;

    private String digitoVerificadorConta;

    private BigDecimal saldo;

    public String getCarteira() {
        return Objects.isNull(carteira) ? "" : carteira.trim();
    }

}