package br.com.ifma.refatorandorumoapadroes.simplefactory.service;


import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricaDeInformacaoNossoNumeroService {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class CalculaDigito {
        private String carteira;
        private long nossoNumero;
        private int size;
    }

    public InformacoesNossoNumero criaInformacaoNossoNumeroParaBradesco(Long nossoNumero,
                                                                        String carteira,
                                                                        Long contaId) {

        CalculaDigito calculaDigito = CalculaDigito.builder()
                .nossoNumero(nossoNumero)
                .carteira(carteira)
                .size(11)
                .build();

        String digitoVerificador = this.calculaDigitoVerificador(calculaDigito, 7);

        return InformacoesNossoNumero.builder()
                .idConta(contaId)
                .carteira(carteira)
                .digitoVerificadorNossoNumero(digitoVerificador)
                .nossoNumero(nossoNumero)
                .build();
    }

    public InformacoesNossoNumero criaInformacaoNossoNumeroParaSantander(Long nossoNumero,
                                                                         String carteira,
                                                                         Long contaId) {


        CalculaDigito calculaDigito = CalculaDigito.builder()
                .nossoNumero(nossoNumero)
                .carteira(carteira)
                .size(12)
                .build();

        String digitoVerificador = this.calculaDigitoVerificador(calculaDigito);

        return InformacoesNossoNumero.builder()
                .idConta(contaId)
                .carteira(carteira)
                .digitoVerificadorNossoNumero(digitoVerificador)
                .nossoNumero(nossoNumero)
                .build();
    }

    public InformacoesNossoNumero criaInformacaoNossoNumeroParaBrasil(Long nossoNumero,
                                                                      String carteira,
                                                                      Long contaId) {

        CalculaDigito calculaDigito = CalculaDigito.builder()
                .nossoNumero(nossoNumero)
                .carteira(carteira)
                .size(11)
                .build();

        String digitoVerificador = this.calculaDigitoVerificador(calculaDigito);
        String  novoNossoNumero = "181817" + StringUtils.leftPad(nossoNumero.toString(), 5, '0');

        return InformacoesNossoNumero.builder()
                .idConta(contaId)
                .carteira(carteira)
                .digitoVerificadorNossoNumero(digitoVerificador)
                .nossoNumero(Long.parseLong(novoNossoNumero))
                .build();
    }

    public InformacoesNossoNumero criaInformacaoNossoNumeroParaSafra(Long nossoNumero,
                                                                     String carteira,
                                                                     Long contaId) {

        CalculaDigito calculaDigito = CalculaDigito.builder()
                .nossoNumero(nossoNumero)
                .carteira(carteira)
                .size(11)
                .build();

        String digitoVerificador = this.calculaDigitoVerificador(calculaDigito, 7);

        return InformacoesNossoNumero.builder()
                .idConta(contaId)
                .carteira(carteira)
                .digitoVerificadorNossoNumero(digitoVerificador)
                .nossoNumero(nossoNumero)
                .build();
    }

    private String calculaDigitoVerificador(CalculaDigito calculaDigito) {

        String composicaoNossoNumero = StringUtils
                .leftPad(Long.toString(calculaDigito.nossoNumero),
                        calculaDigito.size,
                        '0');

        return gerarDigitoMod11Pesos2a9NossoNumeroSantander(composicaoNossoNumero);
    }

    private String calculaDigitoVerificador(CalculaDigito calculaDigito, int base) {

        String composicaoNossoNumero = calculaDigito.carteira + StringUtils.leftPad(Long.toString(calculaDigito.nossoNumero),
                calculaDigito.size,
                '0');

        return calcularDigitoModulo11CnabComBase(composicaoNossoNumero, 7);
    }

    private static String calcularDigitoModulo11CnabComBase(String numero, int base) {
        String padrao = retornarPadrao(numero.length(), 2, base, 2, Ordem.DireitaEsquerda);

        int soma = 0;
        for (int i = 0; i < numero.length(); i++) {
            int valor = Integer.parseInt(new Character(numero.charAt(i)).toString())
                    * Integer.parseInt(new Character(padrao.charAt(i)).toString());
            soma += valor;
        }

        int resto = soma % 11;
        String retorno = "";

        if (resto == 0) {
            retorno = "0";
        } else if (resto == 1) {
            retorno = "P";
        } else {
            int valor = 11 - resto;
            retorno = String.valueOf(valor);
        }

        return retorno;
    }

    private static String retornarPadrao(int qtd, int menorDigito, int maiorDigito, int primeiroNumero, Ordem ordem) {
        String padrao = "";

        if (ordem == Ordem.DireitaEsquerda) {
            for (int i = 0; i < qtd; i++) {
                padrao = String
                        .valueOf(retornarValorIndice(i + primeiroNumero - menorDigito, menorDigito, maiorDigito))
                        + padrao;
            }
        } else {
            for (int i = 0; i < qtd; i++) {
                padrao = String
                        .valueOf(retornarValorIndice(i + primeiroNumero - menorDigito, menorDigito, maiorDigito))
                        + padrao;
            }
        }

        return padrao;
    }

    private static int retornarValorIndice(int indice, int menorDigito, int maiorDigito) {
        int resto = indice % (maiorDigito - menorDigito + 1);
        return resto + menorDigito;
    }

    private static String gerarDigitoMod11Pesos2a9NossoNumeroSantander(String sequenciaNumerica) {
        int[] pesos = {
                2, 3, 4, 5, 6, 7, 8, 9
        };
        List<Integer> digitos = new ArrayList<>();
        for (int i = 0; i < sequenciaNumerica.length(); i++) {
            digitos.add(sequenciaNumerica.charAt(i) - '0');
        }
        Collections.reverse(digitos);
        int soma = 0;
        for (int i = 0, indexPesos = 0; i < digitos.size(); i++, indexPesos = (indexPesos + 1) % pesos.length) {
            int digito = digitos.get(i);
            soma += digito * pesos[indexPesos];
        }
        int resto = soma % 11;
        if (resto == 10) {
            return Integer.toString(1);
        }

        if (resto == 1 || resto == 0) {
            return Integer.toString(0);
        }

        return Integer.toString(11 - resto);
    }

    private static enum Ordem {
        DireitaEsquerda,
        EsquerdaDireita;
    }


}
