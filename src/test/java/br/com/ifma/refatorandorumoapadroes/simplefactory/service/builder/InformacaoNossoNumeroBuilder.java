package br.com.ifma.refatorandorumoapadroes.simplefactory.service.builder;

import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;

import java.util.Arrays;
import java.util.List;

public final class InformacaoNossoNumeroBuilder {

    public static InformacoesNossoNumero criaInformacaoNossoNumeroBB() {
        return InformacoesNossoNumero.builder()
                .nossoNumero(18181700015L)
                .carteira("10")
                .digitoVerificadorNossoNumero("4")
                .idConta(1L)
                .build();
    }

    public static InformacoesNossoNumero criaInformacoesNossoNumeroSantander() {
        return InformacoesNossoNumero.builder()
                .nossoNumero(15L)
                .carteira("11")
                .digitoVerificadorNossoNumero("9")
                .idConta(33L)
                .build();
    }

    public static InformacoesNossoNumero criaInformacoesNossoNumeroBradesco() {
        return InformacoesNossoNumero.builder()
                .nossoNumero(15L)
                .carteira("11")
                .digitoVerificadorNossoNumero("0")
                .idConta(237L)
                .build();
    }

    public static InformacoesNossoNumero criaInformacoesNossoNumeroSafra() {
        return InformacoesNossoNumero.builder()
                .nossoNumero(15L)
                .carteira("12")
                .digitoVerificadorNossoNumero("4")
                .idConta(422L)
                .build();
    }

}
