package br.com.ifma.refatorandorumoapadroes.templatemethod.service;


import br.com.ifma.refatorandorumoapadroes.templatemethod.enumeration.StatusBoletoNossoNumero;
import br.com.ifma.refatorandorumoapadroes.templatemethod.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumero;
import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumeroItMarket;
import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumeroVMix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Objects;

@Slf4j
@Service
public abstract class TemplateControleService {


    public BoletoNossoNumero gerarControleNossoNumero(BoletoNossoNumero boletoItMarket) {

        if (Objects.isNull(boletoItMarket)) {
            throw new PdvValidationException("Boleto ItMarket não gerado.");
        }

        return this.gerarControleNossoNumero(boletoItMarket.getIdFilial(),
                boletoItMarket.getPdv(), boletoItMarket.getNossoNumero());
    }

    public BoletoNossoNumero gerarControleNossoNumero(Long filialId, Integer pdv, Long nossoNumero) {

        BoletoNossoNumero boletoNossoNumero = BoletoNossoNumero.builder()
                .idFilial(filialId)
                .pdv(pdv)
                .data(Calendar.getInstance().getTime())
                .nossoNumero(nossoNumero)
                .status(StatusBoletoNossoNumero.INSERIDO.idStatus)
                .build();

        this.salvaNossoNumero(boletoNossoNumero);
        return boletoNossoNumero;
    }

    protected abstract void salvaNossoNumero(BoletoNossoNumero boletoNossoNumero);

}
