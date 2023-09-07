package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NossoNumeroFabricaService {


    public static String calcularDigitoModulo11CnabComBase(String numero, int base) {
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

    public static String retornarPadrao(int qtd, int menorDigito, int maiorDigito, int primeiroNumero, Ordem ordem) {
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

    public static String gerarDigitoMod11Pesos2a9NossoNumeroSantander(String sequenciaNumerica) {
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

    private static int retornarValorIndice(int indice, int menorDigito, int maiorDigito) {
        int resto = indice % (maiorDigito - menorDigito + 1);
        return resto + menorDigito;
    }

    static enum Ordem {
        DireitaEsquerda,
        EsquerdaDireita;
    }

}
