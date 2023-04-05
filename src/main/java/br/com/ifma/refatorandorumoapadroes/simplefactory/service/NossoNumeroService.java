package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import br.com.ifma.refatorandorumoapadroes.simplefactory.enumeration.SolicitanteNossoNumero;
import br.com.ifma.refatorandorumoapadroes.simplefactory.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.ContaMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.NossoNumeroMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.Conta;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class NossoNumeroService {

    @Autowired
    private NossoNumeroMapper nossoNumeroMapper;

    @Autowired
    private ContaMapper contaMapper;


    private static final long ID_BANCO_DO_BRASIL = 1;
    private static final long ID_BANCO_SANTANDER = 33;
    private static final long ID_BANCO_BRADESCO = 237;
    private static final long ID_BANCO_SAFRA = 422;

    public List<InformacoesNossoNumero> criarNossoNumeroLoja(Long filialId, Integer pdv, Long cupom, Integer qtdeNossoNumero) {

        if (qtdeNossoNumero == null)
            throw new PdvValidationException("qtdeNosso número inválido!");

        List<InformacoesNossoNumero> informacoesNossoNumeros = new ArrayList<>();

        for (int quantidade = 0; quantidade < qtdeNossoNumero; quantidade++) {
            InformacoesNossoNumero informacaoNossoNumero = criarInformacoesNossoNumero(filialId, pdv, cupom);
            informacoesNossoNumeros.add(informacaoNossoNumero);
        }

        return informacoesNossoNumeros;
    }

    private InformacoesNossoNumero criarInformacoesNossoNumero(Long filialId, Integer pdv, Long cupom) {
        Long idConta = nossoNumeroMapper.recuperarIdContaFilial(filialId);

        if (idConta == null || idConta <= 0) {
            throw new PdvValidationException("Conta inválida.");
        }

        Conta conta = contaMapper.recuperarContaPorId(idConta);

        if (conta == null) {
            throw new PdvValidationException("Conta inválida.");
        }

        Long idBanco = contaMapper.recuperarIdBancoPeloIdConta(idConta);

        if (idBanco == null || idBanco <= 0) {
            throw new PdvValidationException("Banco inválido..");
        }

        Calendar data = Calendar.getInstance();
        String carteiraConta = null;
        carteiraConta = conta.getCarteira();
        carteiraConta = carteiraConta == null ? "" : carteiraConta.trim();

        Long nossoNumero = nossoNumeroMapper.gerarNossoNumeroProcedure(idConta, SolicitanteNossoNumero.FRENTE_DE_LOJA.getCodigo(),
                filialId, pdv, data.getTime(), cupom);

        if (idBanco == ID_BANCO_DO_BRASIL) {

            String codigoBeneficiario = contaMapper.recuperarCodigoBeneficiarioPelaConta(idConta);

            if (codigoBeneficiario == null || codigoBeneficiario.trim().isEmpty()) {
                throw new PdvValidationException("Código de beneficiário não cadastrado para o conta: " + idConta);
            }

            nossoNumero = Long.parseLong("181817" + StringUtils.leftPad(nossoNumero.toString(), 5, '0'));
        }

        String digitoVerificadorNossoNumero = null;

        if (idBanco == ID_BANCO_BRADESCO) {
            digitoVerificadorNossoNumero = calcularDigitoModulo11CnabComBase(
                    carteiraConta + StringUtils.leftPad(Long.toString(nossoNumero), 11, '0'), 7);
        } else if (idBanco == ID_BANCO_SANTANDER) {
            digitoVerificadorNossoNumero = gerarDigitoMod11Pesos2a9NossoNumeroSantander(StringUtils.leftPad(
                    nossoNumero.toString(), 12, '0'));
        } else if (idBanco == ID_BANCO_DO_BRASIL) {
            digitoVerificadorNossoNumero = gerarDigitoMod11Pesos2a9NossoNumeroSantander(StringUtils.leftPad(nossoNumero
                    .toString().toString(), 11, '0'));
        } else if (idBanco == ID_BANCO_SAFRA) {
            digitoVerificadorNossoNumero = calcularDigitoModulo11CnabComBase(
                    carteiraConta + StringUtils.leftPad(Long.toString(nossoNumero), 11, '0'), 7);
        }

        InformacoesNossoNumero informacaoNossoNumero = new InformacoesNossoNumero();
        informacaoNossoNumero.setNossoNumero(nossoNumero);
        informacaoNossoNumero.setDigitoVerificadorNossoNumero(digitoVerificadorNossoNumero);
        informacaoNossoNumero.setCarteira(carteiraConta);
        informacaoNossoNumero.setIdConta(idConta);
        return informacaoNossoNumero;
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