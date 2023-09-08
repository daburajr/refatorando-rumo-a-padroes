package br.com.ifma.refatorandorumoapadroes.simplefactory.util;

public class GeraDigitoModulo11CnabComBase {

    private GeraDigitoModulo11CnabComBase() {}

    public static String calcularDigitoModulo11CnabComBase(String numero, int base) {

        String padrao = retornarPadrao(numero.length(), 2, base, 2);

        int soma = 0;
        for (int i = 0; i < numero.length(); i++) {
            int valor = Integer.parseInt(String.valueOf(numero.charAt(i)))
                    * Integer.parseInt(String.valueOf(padrao.charAt(i)));
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

    public static String retornarPadrao(int qtd, int menorDigito, int maiorDigito, int primeiroNumero) {

        StringBuilder padrao = new StringBuilder();

        for (int i = 0; i < qtd; i++) {
            padrao.insert(0, retornarValorIndice(i + primeiroNumero - menorDigito, menorDigito, maiorDigito));
        }

        return padrao.toString();
    }

    private static int retornarValorIndice(int indice, int menorDigito, int maiorDigito) {
        int resto = indice % (maiorDigito - menorDigito + 1);
        return resto + menorDigito;
    }
}
