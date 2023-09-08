package br.com.ifma.refatorandorumoapadroes.templatemethod.service;

import br.com.ifma.refatorandorumoapadroes.templatemethod.mapper.BoletoNossoNumeroItMarketMapper;
import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumero;
import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumeroItMarket;
import org.springframework.stereotype.Service;

@Service
public class ControleNossoNumeroItMarketService extends TemplateControleService {

    private final BoletoNossoNumeroItMarketMapper boletoNossoNumeroItMarketMapper;

    public ControleNossoNumeroItMarketService(BoletoNossoNumeroItMarketMapper boletoNossoNumeroItMarketMapper) {
        this.boletoNossoNumeroItMarketMapper = boletoNossoNumeroItMarketMapper;
    }

    protected void salvaDocumento(BoletoNossoNumero boletoItMarket) {
        boletoNossoNumeroItMarketMapper.inserirBoletoNossoNuemroItMarket((BoletoNossoNumeroItMarket) boletoItMarket);
    }
}
