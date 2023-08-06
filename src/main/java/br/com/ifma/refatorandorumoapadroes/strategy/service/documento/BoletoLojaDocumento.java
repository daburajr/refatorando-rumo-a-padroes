package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BoletoLojaDocumento extends TemplateDocumento {


    protected BoletoLojaDocumento(BoletoImpressaoMapper boletoImpressaoMapper,
                                  IBoletoReports boletoReports,
                                  IBancoCupomClient cupomCapaService) {
        super(boletoImpressaoMapper, boletoReports, cupomCapaService);
    }
}
