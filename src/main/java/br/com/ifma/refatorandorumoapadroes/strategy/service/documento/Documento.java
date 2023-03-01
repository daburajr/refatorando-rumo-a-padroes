package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;

import java.util.List;

public interface Documento {

    boolean executaProcessamento(TipoDocumento tipo);

    void imprime(List<DocumentoItMarket> documentos);

}
