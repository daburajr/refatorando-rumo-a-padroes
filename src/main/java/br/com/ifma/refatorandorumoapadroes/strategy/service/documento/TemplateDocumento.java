package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;

import java.util.List;

public abstract class TemplateDocumento implements Documento {

    protected final BoletoImpressaoMapper boletoImpressaoMapper;
    protected final IBoletoReports boletoReports;
    protected final IBancoCupomClient cupomCapaService;

    private static final Integer INCIDENCIA = 15;

    protected TemplateDocumento(BoletoImpressaoMapper boletoImpressaoMapper,
                                IBoletoReports boletoReports,
                                IBancoCupomClient cupomCapaService) {
        this.boletoImpressaoMapper = boletoImpressaoMapper;
        this.boletoReports = boletoReports;
        this.cupomCapaService = cupomCapaService;
    }

    @Override
    public boolean executaProcessamento(TipoDocumento tipo) {
        return this.pegaTipoDocumento().equals(tipo);
    }

    @Override
    public void imprime(List<DocumentoItMarket> documentos) {
        documentos.forEach(documentoItMarket -> {
            try {
                this.executaOperacaoDeImpressao(documentoItMarket);
                this.atualizarBoletoItMarket(documentoItMarket,
                        TipoStatusImpressao.IMPRESSAO_CONCLUIDA);
            } catch (Exception e) {
                this.registrarIncidenciaEError(documentoItMarket,
                        e.getMessage());
            }
        });
    }

    protected abstract TipoDocumento pegaTipoDocumento();

    protected abstract void executaOperacaoDeImpressao(DocumentoItMarket documentoItMarket);

    private void registrarIncidenciaEError(DocumentoItMarket documentoItMarket,
                                           String mensagemDeErro) {

        documentoItMarket.adicionaIncidencia();

        if (documentoItMarket.getIncidencia() >= INCIDENCIA) {

            this.atualizarBoletoItMarket(documentoItMarket,
                    TipoStatusImpressao.IMPRESSAO_COM_ERRO);

            boletoImpressaoMapper
                    .registrarError(documentoItMarket, mensagemDeErro);
        }

        boletoImpressaoMapper.atualizarBoletoItMarket(documentoItMarket);
    }

    private void atualizarBoletoItMarket(DocumentoItMarket documentoItMarket,
                                         TipoStatusImpressao statusImpressao) {
        documentoItMarket.setTipoStatusImpressao(statusImpressao.getCodigo());
        boletoImpressaoMapper.atualizarBoletoItMarket(documentoItMarket);
    }
}

