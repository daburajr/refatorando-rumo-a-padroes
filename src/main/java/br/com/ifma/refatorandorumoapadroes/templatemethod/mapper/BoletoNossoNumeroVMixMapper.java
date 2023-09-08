package br.com.ifma.refatorandorumoapadroes.templatemethod.mapper;

import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumero;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletoNossoNumeroVMixMapper {

    default void inserirBoletoNossoNumeroVMix(BoletoNossoNumero boletoNossoNumeroVMix) {
        System.out.println("Inseriu");
    }

}
