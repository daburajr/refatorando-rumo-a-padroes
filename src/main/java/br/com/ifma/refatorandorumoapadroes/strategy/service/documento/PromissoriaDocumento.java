package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento.PROMISSORIA;

@Slf4j
@Service
public class PromissoriaDocumento extends TemplateDocumento {


    protected PromissoriaDocumento(BoletoImpressaoMapper boletoImpressaoMapper,
                                   IBoletoReports boletoReports,
                                   IBancoCupomClient cupomCapaService) {
        super(boletoImpressaoMapper, boletoReports, cupomCapaService);
    }

    @Override
    protected TipoDocumento pegaTipoDocumento() {
        return PROMISSORIA;
    }

    @Override
    protected void executaOperacaoDeImpressao(DocumentoItMarket documentoItMarket) {
        boletoReports.imprimirPromissoria(documentoItMarket);
    }
}
