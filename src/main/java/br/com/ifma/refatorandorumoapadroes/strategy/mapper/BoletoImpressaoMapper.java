package br.com.ifma.refatorandorumoapadroes.strategy.mapper;

import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BoletoImpressaoMapper {
    List<DocumentoItMarket> buscarBoletosPedentesDeImpressao();

    void registrarError(DocumentoItMarket documentoItMarket, String mensagemDeErro);

    void atualizarBoletoItMarket(DocumentoItMarket documentoItMarket);
}
