package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;
import br.com.ifma.refatorandorumoapadroes.simplefactory.util.CalculoDigitoVerificadorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NossoNumeroFabricaService {


    public InformacoesNossoNumero criaInformacaoParaSantander(long nossoNumero, String carteiraConta, long contaId) {
        String digitoVerificador = pegaDigitoVerificadorNossoNumero(nossoNumero, 12);
        return getInformacoesNossoNumero(digitoVerificador, nossoNumero, carteiraConta, contaId);
    }

    public InformacoesNossoNumero criaInformacaoParaBancoDoBrasil(Long nossoNumero, String carteiraConta, long contaId) {
        nossoNumero = Long.parseLong("181817" + StringUtils.leftPad(nossoNumero.toString(), 5, '0'));
        String digitoVerificador = pegaDigitoVerificadorNossoNumero(nossoNumero, 11);
        return getInformacoesNossoNumero(digitoVerificador, nossoNumero, carteiraConta, contaId);

    }

    public InformacoesNossoNumero criaInformacaoParaSafraOuBradesco(long nossoNumero, String carteiraConta, long contaId) {
        String digitoVerificador = pegaDigitoVerificadorNossoNumeroComCarteira(carteiraConta, nossoNumero);
        return getInformacoesNossoNumero(digitoVerificador, nossoNumero, carteiraConta, contaId);
    }

    private InformacoesNossoNumero getInformacoesNossoNumero(String digitoVerificadorNossoNumero, long nossoNumero,
                                                             String carteiraConta, long contaId) {

        return InformacoesNossoNumero.builder()
                .nossoNumero(nossoNumero)
                .digitoVerificadorNossoNumero(digitoVerificadorNossoNumero)
                .carteira(carteiraConta)
                .idConta(contaId)
                .build();
    }

    private String pegaDigitoVerificadorNossoNumeroComCarteira(String carteiraConta, Long nossoNumero) {
        return CalculoDigitoVerificadorUtil.calcularDigitoModulo11CnabComBase(
                carteiraConta + StringUtils.leftPad(Long.toString(nossoNumero), 11, '0'), 7);
    }

    private String pegaDigitoVerificadorNossoNumero(Long nossoNumero, int tamanho) {
        return CalculoDigitoVerificadorUtil.gerarDigitoMod11Pesos2a9NossoNumero(StringUtils.leftPad(
                nossoNumero.toString(), tamanho, '0'));
    }

}
