package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class TemplateDocumento implements Documento {

    private static final Integer INCIDENCIA = 15;

    protected final BoletoImpressaoMapper boletoImpressaoMapper;


    @Override
    public boolean executaProcessamento(TipoDocumento tipo) {
        return tipo.equals(this.pegaTipoDocumento());
    }

    @Override
    public void imprime(List<DocumentoItMarket> documentos) {
        documentos.forEach( boletoItMarket -> {
            try {
                this.executaOperacaoDeImpressao(boletoItMarket);
                this.atualizarBoletoItMarket(boletoItMarket, TipoStatusImpressao.IMPRESSAO_CONCLUIDA);
            } catch (Exception e) {
                this.registrarIncidenciaEError(boletoItMarket, e.getMessage());
            }
        });
    }


    protected abstract TipoDocumento pegaTipoDocumento();

    protected abstract void executaOperacaoDeImpressao(DocumentoItMarket boletoItMarket);

    private void atualizarBoletoItMarket(DocumentoItMarket boletoItMarket, TipoStatusImpressao statusImpressao) {
        boletoItMarket.setTipoStatusImpressao(statusImpressao.getCodigo());
        boletoImpressaoMapper.atualizarBoletoItMarket(boletoItMarket);
    }

    private void registrarIncidenciaEError(DocumentoItMarket boletoItMarket, String mensagemDeErro) {

        boletoItMarket.adicionaIncidencia();

        if (boletoItMarket.getIncidencia() >= INCIDENCIA) {
            this.atualizarBoletoItMarket(boletoItMarket, TipoStatusImpressao.IMPRESSAO_COM_ERRO);
            boletoImpressaoMapper.registrarError(boletoItMarket, mensagemDeErro);
        }

        boletoImpressaoMapper.atualizarBoletoItMarket(boletoItMarket);
    }

}
