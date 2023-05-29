package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PromissoriaDocumento extends TemplateDocumento {


    protected PromissoriaDocumento(BoletoImpressaoMapper boletoImpressaoMapper, IBoletoReports boletoReports) {
        super(boletoImpressaoMapper, boletoReports);
    }

    @Override
    protected TipoDocumento pegaTipoDocumento() {
        return TipoDocumento.PROMISSORIA;
    }

    @Override
    protected void executaOperacaoDeImpressao(DocumentoItMarket boletoItMarket) {
        boletoReports.imprimirPromissoria(boletoItMarket);
    }
}
