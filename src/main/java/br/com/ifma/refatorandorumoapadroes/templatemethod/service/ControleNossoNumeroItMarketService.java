package br.com.ifma.refatorandorumoapadroes.templatemethod.service;

import br.com.ifma.refatorandorumoapadroes.templatemethod.mapper.BoletoNossoNumeroItMarketMapper;
import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumero;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ControleNossoNumeroItMarketService extends TemplateControleService {

    private final BoletoNossoNumeroItMarketMapper boletoNossoNumeroItMarketMapper;

    protected void salvaNossoNumero(BoletoNossoNumero boletoNossoNumero) {
        boletoNossoNumeroItMarketMapper
                .inserirBoletoNossoNuemroItMarket(boletoNossoNumero);
    }
}
