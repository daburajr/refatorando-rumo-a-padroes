package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoletoLojaDocumento implements Documento {

    private static final TipoDocumento TIPO_DOCUMENTO = TipoDocumento.BOLETO_LOJA;

    private static final Integer INCIDENCIA = 15;

    private final BoletoImpressaoMapper boletoImpressaoMapper;
    private final IBoletoReports boletoReports;

    @Override
    public boolean executaProcessamento(TipoDocumento tipo) {
        return TIPO_DOCUMENTO.equals(tipo);
    }

    @Override
    public void imprime(List<DocumentoItMarket> documentos) {
        documentos.forEach( boletoItMarket -> {
            try {
                boletoReports.imprimirBoletoLoja(boletoItMarket);
                this.atualizarBoletoItMarket(boletoItMarket, TipoStatusImpressao.IMPRESSAO_CONCLUIDA);
            } catch (Exception e) {
                this.registrarIncidenciaEError(boletoItMarket, e.getMessage());
            }
        });
    }

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
