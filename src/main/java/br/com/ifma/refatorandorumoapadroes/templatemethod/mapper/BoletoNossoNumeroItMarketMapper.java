package br.com.ifma.refatorandorumoapadroes.templatemethod.mapper;

import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumeroItMarket;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletoNossoNumeroItMarketMapper {

    default void inserirBoletoNossoNuemroItMarket(BoletoNossoNumeroItMarket boletoItMarket) {
        System.out.println("Inseriu");
    }

}
