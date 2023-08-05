package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;

import java.util.List;

import static br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto.*;

public class BoletoLojaDocumento implements Documento {

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

}
