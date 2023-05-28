package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;

import java.util.List;

public abstract class TemplateDocumento implements Documento {

    @Override
    public boolean executaProcessamento(TipoDocumento tipo) {
        return true;
    }

    @Override
    public void imprime(List<DocumentoItMarket> documentos) {

    }
}
