package br.com.ifma.refatorandorumoapadroes.strategy.service.builder;

import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoletoBuilder {

    private BoletoBuilder() {}

    public static BoletoItMarket boletoLojaPendente() {
        return BoletoItMarket.builder()
                .id(1L)
                .filialId(1L)
                .pdv(700)
                .cupom(3570L)
                .finalizadora(5)
                .valorBoleto(BigDecimal.valueOf(250))
                .cpfOuCnpj("60607070787877")
                .dataDocumento(LocalDateTime.now().toString())
                .dataVencimento(LocalDateTime.now().toString())
                .nossoNumero(4554545L)
                .idCliente(1L)
                .idImpressora(210L)
                .tipoStatusImpressao(TipoStatusImpressao.IMPRESSAO_PEDENTE.getCodigo())
                .tipoBoleto(TipoBoleto.BOLETO_LOJA.getCodigo())
                .idPedido(789040090L)
                .incidencia(0)
                .dataMovimento(LocalDate.now())
                .cpfProcurador(687877078909L)
                .build();
    }

    public static BoletoItMarket boletoBalcaoPendente() {
        return BoletoItMarket.builder()
                .id(2L)
                .filialId(1L)
                .pdv(700)
                .cupom(3570L)
                .finalizadora(5)
                .valorBoleto(BigDecimal.valueOf(250))
                .cpfOuCnpj("60607070787877")
                .dataDocumento(LocalDateTime.now().toString())
                .dataVencimento(LocalDateTime.now().toString())
                .nossoNumero(4554545L)
                .idCliente(1L)
                .idImpressora(210L)
                .tipoStatusImpressao(TipoStatusImpressao.IMPRESSAO_PEDENTE.getCodigo())
                .tipoBoleto(TipoBoleto.BOLETO_BALCAO.getCodigo())
                .incidencia(0)
                .dataMovimento(LocalDate.now())
                .cpfProcurador(687877078909L)
                .build();
    }

    public static BoletoItMarket carnePendente() {
        return BoletoItMarket.builder()
                .id(3L)
                .filialId(1L)
                .pdv(700)
                .cupom(3570L)
                .finalizadora(5)
                .valorBoleto(BigDecimal.valueOf(250))
                .cpfOuCnpj("60607070787877")
                .dataDocumento(LocalDateTime.now().toString())
                .dataVencimento(LocalDateTime.now().toString())
                .nossoNumero(4554545L)
                .idCliente(1L)
                .idImpressora(210L)
                .tipoStatusImpressao(TipoStatusImpressao.IMPRESSAO_PEDENTE.getCodigo())
                .tipoBoleto(TipoBoleto.CARNE.getCodigo())
                .idPedido(789040090L)
                .incidencia(0)
                .dataMovimento(LocalDate.now())
                .cpfProcurador(687877078909L)
                .build();
    }

    public static BoletoItMarket promissoriaPendente() {
        return BoletoItMarket.builder()
                .id(4L)
                .filialId(1L)
                .pdv(700)
                .cupom(3570L)
                .finalizadora(5)
                .valorBoleto(BigDecimal.valueOf(250))
                .cpfOuCnpj("60607070787877")
                .dataDocumento(LocalDateTime.now().toString())
                .dataVencimento(LocalDateTime.now().toString())
                .nossoNumero(4554545L)
                .idCliente(1L)
                .idImpressora(210L)
                .tipoStatusImpressao(TipoStatusImpressao.IMPRESSAO_PEDENTE.getCodigo())
                .tipoBoleto(TipoBoleto.PROMISSORIA.getCodigo())
                .idPedido(789040090L)
                .incidencia(0)
                .dataMovimento(LocalDate.now())
                .cpfProcurador(687877078909L)
                .build();
    }

    public static List<BoletoItMarket> pegaBoletos() {
        return Arrays.asList(promissoriaPendente(), carnePendente(), boletoBalcaoPendente(), boletoLojaPendente());
    }

    public static List<BoletoItMarket> pegaBoletosSemBoletoLoja() {
        return Arrays.asList(promissoriaPendente(), carnePendente(), boletoBalcaoPendente());
    }

}
