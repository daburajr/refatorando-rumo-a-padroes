package br.com.ifma.refatorandorumoapadroes.simplefactory.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GeraDigitoMod11Util {

    private static final int MOD = 11;
    private static final int CHARACTER_ZERO = 48;

    private GeraDigitoMod11Util() {}

    public static String geraDigito(String sequenciaNumerica) {

        int[] pesos = { 2, 3, 4, 5, 6, 7, 8, 9};

        List<Integer> digitos = pegaDigitosReversos(sequenciaNumerica);

        return calculaDigito(digitos, pesos);
    }

    private static String calculaDigito(List<Integer> digitos, int[] pesos) {

        int soma = pegaSomaComBaseNosPesos(digitos, pesos);

        int resto = soma % MOD;

        if (resto == 10) {
            return Integer.toString(1);
        } else if (resto == 1 || resto == 0) {
            return Integer.toString(0);
        } else {
            return Integer.toString(11 - resto);
        }

    }

    private static int pegaSomaComBaseNosPesos(List<Integer> digitos, int[] pesos) {

        int soma = 0;
        int indexPesos = 0;

        for (int digito : digitos) {
            soma += digito * pesos[indexPesos];
            indexPesos = (indexPesos + 1) % pesos.length;
        }

        return soma;
    }

    private static List<Integer> pegaDigitosReversos(String sequenciaNumerica) {

        List<Integer> digitos = sequenciaNumerica.chars()
                .mapToObj(p -> p - CHARACTER_ZERO)
                .collect(Collectors.toList());

        Collections.reverse(digitos);

        return digitos;
    }


}
