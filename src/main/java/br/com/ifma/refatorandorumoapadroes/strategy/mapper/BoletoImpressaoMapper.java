package br.com.ifma.refatorandorumoapadroes.strategy.mapper;

import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;

import java.util.List;

public interface BoletoImpressaoMapper {
    List<DocumentoItMarket> buscarBoletosPedentesDeImpressao();

    void registrarError(DocumentoItMarket boletoItMarket, String mensagemDeErro);

    void atualizarBoletoItMarket(DocumentoItMarket boletoItMarket);
}
