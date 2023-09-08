package br.com.ifma.refatorandorumoapadroes.templatemethod.service;

import br.com.ifma.refatorandorumoapadroes.templatemethod.mapper.BoletoNossoNumeroItMarketMapper;
import org.springframework.stereotype.Service;

@Service
public class ControleNossoNumeroItMarketService extends TemplateControleService {

    public ControleNossoNumeroItMarketService(BoletoNossoNumeroItMarketMapper boletoNossoNumeroItMarketMapper) {
        super(boletoNossoNumeroItMarketMapper);

    }
}
