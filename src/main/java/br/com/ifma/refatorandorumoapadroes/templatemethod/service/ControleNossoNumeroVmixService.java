package br.com.ifma.refatorandorumoapadroes.templatemethod.service;


import br.com.ifma.refatorandorumoapadroes.templatemethod.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.templatemethod.mapper.BoletoNossoNumeroVMixMapper;
import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumeroItMarket;
import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumeroVMix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ControleNossoNumeroVmixService {


    private final BoletoNossoNumeroVMixMapper boletoNossoNumeroVMixMapper;

    public BoletoNossoNumeroVMix gerarControleNossoNumero(BoletoNossoNumeroItMarket boletoItMarket) {

        if(boletoItMarket == null) {
            throw new PdvValidationException("Boleto ItMarket não gerado.");
        }

        BoletoNossoNumeroVMix boletoVMix = BoletoNossoNumeroVMix
                .builder()
                .idFilial(boletoItMarket.getIdFilial())
                .pdv(boletoItMarket.getPdv())
                .data(boletoItMarket.getData())
                .nossoNumero(boletoItMarket.getNossoNumero())
                .status(boletoItMarket.getStatus())
                .build();

        boletoNossoNumeroVMixMapper.inserirBoletoNossoNumeroVMix(boletoVMix);

        return boletoVMix;
    }
}
