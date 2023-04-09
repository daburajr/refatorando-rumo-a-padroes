package br.com.ifma.refatorandorumoapadroes.simplefactory.service;


import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;
import br.com.ifma.refatorandorumoapadroes.simplefactory.util.DigitoVerificadorUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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

        return pegaInformacoesNossoNumeroComBase(nossoNumero, carteira, contaId);
    }

    public InformacoesNossoNumero criaInformacaoNossoNumeroParaSantander(Long nossoNumero,
                                                                         String carteira,
                                                                         Long contaId) {


        CalculaDigito calculaDigito = CalculaDigito.builder()
                .nossoNumero(nossoNumero)
                .carteira(carteira)
                .size(12)
                .build();

        String digitoVerificador = calculaDigitoVerificador(calculaDigito);

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


        String  novoNossoNumero = "181817" + StringUtils.leftPad(nossoNumero.toString(), 5, '0');

        CalculaDigito calculaDigito = CalculaDigito.builder()
                .nossoNumero(Long.parseLong(novoNossoNumero))
                .carteira(carteira)
                .size(11)
                .build();

        String digitoVerificador = calculaDigitoVerificador(calculaDigito);

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

        return pegaInformacoesNossoNumeroComBase(nossoNumero, carteira, contaId);
    }

    private InformacoesNossoNumero pegaInformacoesNossoNumeroComBase(Long nossoNumero, String carteira, Long contaId) {

        CalculaDigito calculaDigito = CalculaDigito.builder()
                .nossoNumero(nossoNumero)
                .carteira(carteira)
                .size(11)
                .build();

        String digitoVerificador = calculaDigitoVerificador(calculaDigito, 7);

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

        return DigitoVerificadorUtil.gerarDigitoMod11Pesos2a9NossoNumero(composicaoNossoNumero);
    }

    private String calculaDigitoVerificador(CalculaDigito calculaDigito, int base) {

        String composicaoNossoNumero = calculaDigito.carteira + StringUtils.leftPad(Long.toString(calculaDigito.nossoNumero),
                calculaDigito.size,
                '0');

        return DigitoVerificadorUtil.calcularDigitoModulo11CnabComBase(composicaoNossoNumero, base);
    }




}
