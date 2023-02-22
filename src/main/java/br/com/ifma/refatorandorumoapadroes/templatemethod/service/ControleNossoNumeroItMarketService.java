package br.com.ifma.refatorandorumoapadroes.templatemethod.service;

import br.com.ifma.refatorandorumoapadroes.templatemethod.enumeration.StatusBoletoNossoNumero;
import br.com.ifma.refatorandorumoapadroes.templatemethod.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.templatemethod.mapper.BoletoNossoNumeroItMarketMapper;
import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumeroItMarket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class ControleNossoNumeroItMarketService {

    private final BoletoNossoNumeroItMarketMapper boletoNossoNumeroItMarketMapper;

    public BoletoNossoNumeroItMarket gerarControleNossoNumero(Long filialId, Integer pdv, Long nossoNumero) {

        Calendar data = Calendar.getInstance();
        BoletoNossoNumeroItMarket boletoItMarket = BoletoNossoNumeroItMarket.builder()
                .idFilial(filialId)
                .pdv(pdv)
                .data(data.getTime())
                .nossoNumero(nossoNumero)
                .status(StatusBoletoNossoNumero.INSERIDO.idStatus)
                .build();

        if(boletoItMarket == null) {
            throw new PdvValidationException("Não foi possível criar boleto ItMarket.");
        }

        boletoNossoNumeroItMarketMapper.inserirBoletoNossoNuemroItMarket(boletoItMarket);

        return boletoItMarket;
    }

}
