package br.com.ifma.refatorandorumoapadroes.strategy.client;

import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;

public interface IBoletoReports {
    void imprimirBoletoLoja(DocumentoItMarket boletoItMarket);

    void imprimirBoletoBalcao(DocumentoItMarket boletoItMarket);

    void imprimirCarne(DocumentoItMarket carne);

    void imprimirPromissoria(DocumentoItMarket promissoria);
}
