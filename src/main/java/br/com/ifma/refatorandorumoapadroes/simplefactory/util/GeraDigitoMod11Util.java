package br.com.ifma.refatorandorumoapadroes.simplefactory.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeraDigitoMod11Util {

    public static String geraDigito(String sequenciaNumerica) {
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



}
