package br.com.ifma.refatorandorumoapadroes.strategy.client;

import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;
import org.springframework.stereotype.Service;

@Service
public interface IBoletoReports {
    void imprimirBoletoLoja(DocumentoItMarket documentoItMarket);

    void imprimirBoletoBalcao(DocumentoItMarket documentoItMarket);

    void imprimirCarne(DocumentoItMarket carne);

    void imprimirPromissoria(DocumentoItMarket promissoria);
}
