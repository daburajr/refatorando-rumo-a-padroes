package br.com.ifma.refatorandorumoapadroes.strategy.mapper;

import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;

import java.util.List;

public interface BoletoImpressaoMapper {
    List<BoletoItMarket> buscarBoletosPedentesDeImpressao();

    void registrarError(BoletoItMarket boletoItMarket, String mensagemDeErro);

    void atualizarBoletoItMarket(BoletoItMarket boletoItMarket);
}
