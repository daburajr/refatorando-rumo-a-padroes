package br.com.ifma.refatorandorumoapadroes.strategy.mapper;

import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BoletoImpressaoMapper {
    List<BoletoItMarket> buscarBoletosPedentesDeImpressao();

    void registrarError(BoletoItMarket boletoItMarket, String mensagemDeErro);

    void atualizarBoletoItMarket(BoletoItMarket boletoItMarket);
}
