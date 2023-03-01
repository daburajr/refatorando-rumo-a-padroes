package br.com.ifma.refatorandorumoapadroes.strategy.service;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;
import br.com.ifma.refatorandorumoapadroes.strategy.model.CupomCapaDTO;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.BoletoBalcaoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.BoletoLojaDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.CarneDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.PromissoriaDocumento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImpressaoBoletoService {

    private final BoletoImpressaoMapper boletoImpressaoMapper;

    private final BoletoLojaDocumento boletoLojaDocumento;

    private final CarneDocumento carneDocumento;

    private final PromissoriaDocumento promissoriaDocumento;

    private final BoletoBalcaoDocumento boletoBalcaoDocumento;


    public void imprimirBoletos() {

        List<BoletoItMarket> boletos = boletoImpressaoMapper.buscarBoletosPedentesDeImpressao();

        if (boletos.isEmpty()) return;

        log.info("Inicio da impessão de boletos - Qtde: " + boletos.size() + " Ids: " + boletos.stream().map(BoletoItMarket::getId).collect(Collectors.toList()));
        List<BoletoItMarket> boletosLoja  = this.pegaBoletosDe(TipoBoleto.BOLETO_LOJA, boletos);
        List<BoletoItMarket> boletosBalcao = this.pegaBoletosDe(TipoBoleto.BOLETO_BALCAO, boletos);
        List<BoletoItMarket> promissorias = this.pegaBoletosDe(TipoBoleto.PROMISSORIA, boletos);
        List<BoletoItMarket> carnes = this.pegaBoletosDe(TipoBoleto.CARNE, boletos);

        if (!boletosLoja.isEmpty()) {
            this.boletoLojaDocumento.imprime(boletosLoja);
        }

        if (!boletosBalcao.isEmpty()) {
            boletoBalcaoDocumento.imprime(boletosBalcao);
        }

        if (!promissorias.isEmpty()) {
            promissoriaDocumento.imprime(promissorias);
        }

        if (!carnes.isEmpty()) {
            carneDocumento.imprime(carnes);
        }

    }

    private List<BoletoItMarket> pegaBoletosDe(TipoBoleto tipoBoleto, List<BoletoItMarket> boletos) {
        return boletos
                .stream()
                .filter(boletoItMarket -> boletoItMarket.getTipoBoleto().equals(tipoBoleto.getCodigo()))
                .collect(Collectors.toList());
    }

}