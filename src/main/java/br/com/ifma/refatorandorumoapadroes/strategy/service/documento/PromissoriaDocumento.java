package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PromissoriaDocumento extends TemplateDocumento {

    private static final TipoDocumento TIPO_DOCUMENTO = TipoDocumento.PROMISSORIA;
    private final IBoletoReports boletoReports;

    public PromissoriaDocumento(BoletoImpressaoMapper boletoImpressaoMapper,
                                IBoletoReports boletoReports) {
        super(boletoImpressaoMapper);
        this.boletoReports = boletoReports;
    }

    @Override
    protected TipoDocumento pegaTipoDocumento() {
        return TIPO_DOCUMENTO;
    }

    @Override
    protected void executaOperacaoDeImpressao(DocumentoItMarket boletoItMarket) {
        boletoReports.imprimirPromissoria(boletoItMarket);
    }
}
