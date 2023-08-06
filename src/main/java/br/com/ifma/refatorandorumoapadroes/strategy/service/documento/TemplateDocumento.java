package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;

import java.util.List;

import static br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto.BOLETO_LOJA;

public abstract class TemplateDocumento implements Documento {

    private final BoletoImpressaoMapper boletoImpressaoMapper;
    private final IBoletoReports boletoReports;
    private final IBancoCupomClient cupomCapaService;

    private static final Integer INCIDENCIA = 15;

    protected TemplateDocumento(BoletoImpressaoMapper boletoImpressaoMapper,
                                IBoletoReports boletoReports,
                                IBancoCupomClient cupomCapaService) {
        this.boletoImpressaoMapper = boletoImpressaoMapper;
        this.boletoReports = boletoReports;
        this.cupomCapaService = cupomCapaService;
    }

    @Override
    public boolean executaProcessamento(TipoBoleto tipo) {
        return BOLETO_LOJA.equals(tipo);
    }

    @Override
    public void imprime(List<BoletoItMarket> documentos) {
        documentos.forEach(boletoItMarket -> {
            try {
                boletoReports.imprimirBoletoLoja(boletoItMarket);
                this.atualizarBoletoItMarket(boletoItMarket,
                        TipoStatusImpressao.IMPRESSAO_CONCLUIDA);
            } catch (Exception e) {
                this.registrarIncidenciaEError(boletoItMarket,
                        e.getMessage());
            }
        });
    }

    private void registrarIncidenciaEError(BoletoItMarket boletoItMarket,
                                           String mensagemDeErro) {

        boletoItMarket.adicionaIncidencia();

        if (boletoItMarket.getIncidencia() >= INCIDENCIA) {

            this.atualizarBoletoItMarket(boletoItMarket,
                    TipoStatusImpressao.IMPRESSAO_COM_ERRO);

            boletoImpressaoMapper
                    .registrarError(boletoItMarket, mensagemDeErro);
        }

        boletoImpressaoMapper.atualizarBoletoItMarket(boletoItMarket);
    }

    private void atualizarBoletoItMarket(BoletoItMarket boletoItMarket,
                                         TipoStatusImpressao statusImpressao) {
        boletoItMarket.setTipoStatusImpressao(statusImpressao.getCodigo());
        boletoImpressaoMapper.atualizarBoletoItMarket(boletoItMarket);
    }
}

