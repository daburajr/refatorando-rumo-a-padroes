package br.com.ifma.refatorandorumoapadroes.strategy.client;

import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;

public interface IBoletoReports {
    void imprimirBoletoLoja(BoletoItMarket boletoItMarket);

    void imprimirBoletoBalcao(BoletoItMarket boletoItMarket);

    void imprimirCarne(BoletoItMarket carne);

    void imprimirPromissoria(BoletoItMarket promissoria);
}
