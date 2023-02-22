package br.com.ifma.refatorandorumoapadroes.strategy.service;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;
import br.com.ifma.refatorandorumoapadroes.strategy.model.CupomCapaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImpressaoBoletoService {

    private final BoletoImpressaoMapper boletoImpressaoMapper;
    private final IBoletoReports boletoReports;
    private final IBancoCupomClient cupomCapaService;

    private static final Integer INCIDENCIA = 15;

    @Autowired
    public ImpressaoBoletoService(BoletoImpressaoMapper boletoImpressaoMapper,
                                  IBoletoReports boletoReports,
                                  IBancoCupomClient cupomCapaService) {
        this.boletoImpressaoMapper = boletoImpressaoMapper;
        this.boletoReports = boletoReports;
        this.cupomCapaService = cupomCapaService;
    }


    public void imprimirBoletos() {
        List<BoletoItMarket> boletos = boletoImpressaoMapper.buscarBoletosPedentesDeImpressao();

        if (boletos.isEmpty()) return;

        log.info("Inicio da impessão de boletos - Qtde: " + boletos.size() + " Ids: " + boletos.stream().map(BoletoItMarket::getId).collect(Collectors.toList()));
        List<BoletoItMarket> boletosLoja  = this.pegaBoletosDe(TipoBoleto.BOLETO_LOJA, boletos);
        List<BoletoItMarket> boletosBalcao = this.pegaBoletosDe(TipoBoleto.BOLETO_BALCAO, boletos);
        List<BoletoItMarket> promissorias = this.pegaBoletosDe(TipoBoleto.PROMISSORIA, boletos);
        List<BoletoItMarket> carnes = this.pegaBoletosDe(TipoBoleto.CARNE, boletos);

        if (!boletosLoja.isEmpty()) {
            this.imprimirBoletosLoja(boletosLoja);
        }

        if (!boletosBalcao.isEmpty()) {
            this.imprimirBoletosBalcao(boletosBalcao);
        }

        if (!promissorias.isEmpty()) {
            this.imprimirPromissorias(promissorias);
        }

        if (!carnes.isEmpty()) {
            this.imprimirCarnes(carnes);
        }

    }


    private void imprimirBoletosLoja(List<BoletoItMarket> boletosLoja) {
        boletosLoja.forEach( boletoItMarket -> {
            try {
                boletoReports.imprimirBoletoLoja(boletoItMarket);
                this.atualizarBoletoItMarket(boletoItMarket, TipoStatusImpressao.IMPRESSAO_CONCLUIDA);
            } catch (Exception e) {
                this.registrarIncidenciaEError(boletoItMarket, e.getMessage());
            }
        });
    }

    private void imprimirBoletosBalcao(List<BoletoItMarket> boletosBalcao) {
        boletosBalcao.forEach(boletoItMarket -> {
            try {
                this.buscarInformacoesAdicionais(boletoItMarket);
                boletoReports.imprimirBoletoBalcao(boletoItMarket);
                this.atualizarBoletoItMarket(boletoItMarket, TipoStatusImpressao.IMPRESSAO_CONCLUIDA);
            } catch (Exception e) {
                this.registrarIncidenciaEError(boletoItMarket, e.getMessage());
            }
        });
    }

    private void imprimirPromissorias(List<BoletoItMarket> promissorias) {
        promissorias.forEach(promissoria -> {
            try {
                boletoReports.imprimirPromissoria(promissoria);
                this.atualizarBoletoItMarket(promissoria, TipoStatusImpressao.IMPRESSAO_CONCLUIDA);
            } catch (Exception e) {
                this.registrarIncidenciaEError(promissoria, e.getMessage());
            }
        });
    }

    private void imprimirCarnes(List<BoletoItMarket> carnes) {
        carnes.forEach(carne -> {
            try {
                boletoReports.imprimirCarne(carne);
                this.atualizarBoletoItMarket(carne, TipoStatusImpressao.IMPRESSAO_CONCLUIDA);
            } catch (Exception e) {
                this.registrarIncidenciaEError(carne, e.getMessage());
            }
        });
    }

    private List<BoletoItMarket> pegaBoletosDe(TipoBoleto tipoBoleto, List<BoletoItMarket> boletos) {
        return boletos
                .stream()
                .filter(boletoItMarket -> boletoItMarket.getTipoBoleto().equals(tipoBoleto.getCodigo()))
                .collect(Collectors.toList());
    }

    private void registrarIncidenciaEError(BoletoItMarket boletoItMarket, String mensagemDeErro) {

        boletoItMarket.adicionaIncidencia();

        if (boletoItMarket.getIncidencia() >= INCIDENCIA) {
            this.atualizarBoletoItMarket(boletoItMarket, TipoStatusImpressao.IMPRESSAO_COM_ERRO);
            boletoImpressaoMapper.registrarError(boletoItMarket, mensagemDeErro);
        }

        boletoImpressaoMapper.atualizarBoletoItMarket(boletoItMarket);
    }

    private void atualizarBoletoItMarket(BoletoItMarket boletoItMarket, TipoStatusImpressao statusImpressao) {
        boletoItMarket.setTipoStatusImpressao(statusImpressao.getCodigo());
        boletoImpressaoMapper.atualizarBoletoItMarket(boletoItMarket);
    }

    private void buscarInformacoesAdicionais(BoletoItMarket boletoItMarket) {

        if (Objects.isNull(boletoItMarket.getIdPedido()) || Objects.isNull(boletoItMarket.getDataMovimento())) {

            CupomCapaDTO cupomCapaDTO = cupomCapaService.buscarCupomCapa(boletoItMarket.getFilialId(),
                    boletoItMarket.getPdv(), boletoItMarket.getCupom());

            final String recurso = "service=/boletoservice/ImpressaoBoletoService::imprimirBoletosBalcao::validarBoletoBalcao";

            if (Objects.isNull(cupomCapaDTO))
                throw new PdvValidationException("Cupom pendente de integração ou não encontrado: " + recurso);

            boletoItMarket.setIdPedido(cupomCapaDTO.getPedidoFaturado());
            boletoItMarket.setDataMovimento(cupomCapaDTO.getDataMovimento().toLocalDate());
        }
    }
}