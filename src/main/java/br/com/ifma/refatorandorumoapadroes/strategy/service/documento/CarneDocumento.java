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

import static br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento.*;
import static br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarneDocumento implements Documento {

    private static final Integer INCIDENCIA = 15;
    private final BoletoImpressaoMapper boletoImpressaoMapper;
    private final IBoletoReports boletoReports;

    @Override
    public boolean executaProcessamento(TipoDocumento tipo) {
        return CARNE.equals(tipo);
    }

    @Override
    public void imprime(List<DocumentoItMarket> documentos) {
        documentos.forEach(documentoItMarket -> {
            try {
                boletoReports.imprimirCarne(documentoItMarket);
                this.atualizarBoletoItMarket(documentoItMarket, IMPRESSAO_CONCLUIDA);
            } catch (Exception e) {
                this.registrarIncidenciaEError(documentoItMarket, e.getMessage());
            }
        });
    }

    private void atualizarBoletoItMarket(DocumentoItMarket documentoItMarket, TipoStatusImpressao statusImpressao) {
        documentoItMarket.setTipoStatusImpressao(statusImpressao.getCodigo());
        boletoImpressaoMapper.atualizarBoletoItMarket(documentoItMarket);
    }

    private void registrarIncidenciaEError(DocumentoItMarket documentoItMarket, String mensagemDeErro) {

        documentoItMarket.adicionaIncidencia();

        if (documentoItMarket.getIncidencia() >= INCIDENCIA) {
            this.atualizarBoletoItMarket(documentoItMarket, IMPRESSAO_COM_ERRO);
            boletoImpressaoMapper.registrarError(documentoItMarket, mensagemDeErro);
        }

        boletoImpressaoMapper.atualizarBoletoItMarket(documentoItMarket);
    }

}
