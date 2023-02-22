package br.com.ifma.refatorandorumoapadroes.templatemethod.mapper;

import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumeroVMix;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletoNossoNumeroVMixMapper {

    default void inserirBoletoNossoNumeroVMix(BoletoNossoNumeroVMix boletoNossoNumeroVMix) {
        System.out.println("Inseriu");
    }

}
