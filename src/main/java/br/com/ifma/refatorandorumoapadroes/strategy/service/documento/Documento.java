package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto;
import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;

import java.util.List;

public interface Documento {

    boolean executaProcessamento(TipoBoleto tipo);

    void imprime(List<BoletoItMarket> documentos);

}
