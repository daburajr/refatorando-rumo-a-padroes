package br.com.ifma.refatorandorumoapadroes.simplefactory.service.builder;

import br.com.ifma.refatorandorumoapadroes.simplefactory.model.Conta;

import java.math.BigDecimal;

public final class ContaBuilder {

    public static Conta criaContaBB() {
        return Conta.builder()
                .id(1L)
                .digitoVerificadorConta("0")
                .saldo(BigDecimal.valueOf(250))
                .carteira("10")
                .digitoVerificadorConta("9")
                .build();
    }

    public static Conta criaContaSantander() {
        return Conta.builder()
                .id(33L)
                .digitoVerificadorConta("0")
                .saldo(BigDecimal.valueOf(250))
                .carteira("11")
                .digitoVerificadorConta("9")
                .build();
    }

    public static Conta criaContaBradesco() {
        return Conta.builder()
                .id(237L)
                .digitoVerificadorConta("0")
                .saldo(BigDecimal.valueOf(250))
                .carteira("11")
                .digitoVerificadorConta("9")
                .build();
    }


    public static Conta criaContaSafra() {
        return Conta.builder()
                .id(422L)
                .digitoVerificadorConta("0")
                .saldo(BigDecimal.valueOf(250))
                .carteira("12")
                .digitoVerificadorConta("5")
                .build();
    }
}
