package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;
import org.apache.commons.lang3.StringUtils;

public class NossoNumeroFabricaService {

    public static InformacoesNossoNumero criaInformacaoParaBradesco(long nossoNumero, String carteiraConta, long contaId) {

        String digitoVerificadorNossoNumero = pegaDigitoVerificadorNossoNumeroComCarteira(carteiraConta, nossoNumero);

        InformacoesNossoNumero informacaoNossoNumero = new InformacoesNossoNumero();
        informacaoNossoNumero.setNossoNumero(nossoNumero);
        informacaoNossoNumero.setDigitoVerificadorNossoNumero(digitoVerificadorNossoNumero);
        informacaoNossoNumero.setCarteira(carteiraConta);
        informacaoNossoNumero.setIdConta(contaId);

        return informacaoNossoNumero;
    }

    public static InformacoesNossoNumero criaInformacaoParaSantander(long nossoNumero, String carteiraConta, long contaId) {

        String digitoVerificadorNossoNumero = pegaDigitoVerificadorNossoNumero(nossoNumero, 12);

        InformacoesNossoNumero informacaoNossoNumero = new InformacoesNossoNumero();
        informacaoNossoNumero.setNossoNumero(nossoNumero);
        informacaoNossoNumero.setDigitoVerificadorNossoNumero(digitoVerificadorNossoNumero);
        informacaoNossoNumero.setCarteira(carteiraConta);
        informacaoNossoNumero.setIdConta(contaId);


        return informacaoNossoNumero;
    }

    public static InformacoesNossoNumero criaInformacaoParaBancoDoBrasil(Long nossoNumero, String carteiraConta, long contaId) {
        nossoNumero = Long.parseLong("181817" + StringUtils.leftPad(nossoNumero.toString(), 5, '0'));
        String digitoVerificadorNossoNumero = pegaDigitoVerificadorNossoNumero(nossoNumero, 11);

        InformacoesNossoNumero informacaoNossoNumero = new InformacoesNossoNumero();
        informacaoNossoNumero.setNossoNumero(nossoNumero);
        informacaoNossoNumero.setDigitoVerificadorNossoNumero(digitoVerificadorNossoNumero);
        informacaoNossoNumero.setCarteira(carteiraConta);
        informacaoNossoNumero.setIdConta(contaId);

        return informacaoNossoNumero;

    }

    public static InformacoesNossoNumero criaInformacaoParaSafra(long nossoNumero, String carteiraConta, long contaId) {
        String digitoVerificadorNossoNumero = pegaDigitoVerificadorNossoNumeroComCarteira(carteiraConta, nossoNumero);

        InformacoesNossoNumero informacaoNossoNumero = new InformacoesNossoNumero();
        informacaoNossoNumero.setNossoNumero(nossoNumero);
        informacaoNossoNumero.setDigitoVerificadorNossoNumero(digitoVerificadorNossoNumero);
        informacaoNossoNumero.setCarteira(carteiraConta);
        informacaoNossoNumero.setIdConta(contaId);

        return informacaoNossoNumero;
    }

    private static String pegaDigitoVerificadorNossoNumeroComCarteira(String carteiraConta, Long nossoNumero) {
        return CalculoDigitoVerificadorService.calcularDigitoModulo11CnabComBase(
                carteiraConta + StringUtils.leftPad(Long.toString(nossoNumero), 11, '0'), 7);
    }

    private static String pegaDigitoVerificadorNossoNumero(Long nossoNumero, int tamanho) {
        return CalculoDigitoVerificadorService.gerarDigitoMod11Pesos2a9NossoNumeroSantander(StringUtils.leftPad(
                nossoNumero.toString(), tamanho, '0'));
    }

}
