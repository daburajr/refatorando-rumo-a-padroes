package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto;
import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;

import java.util.List;

public class BoletoLojaDocumento implements Documento {

    @Override
    public boolean executaProcessamento(TipoBoleto tipo) {
        return false;
    }

    @Override
    public void imprime(List<BoletoItMarket> documentos) {

    }

}
