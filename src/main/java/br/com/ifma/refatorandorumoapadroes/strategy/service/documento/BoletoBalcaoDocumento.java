package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.CupomCapaDTO;
import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class BoletoBalcaoDocumento extends TemplateDocumento {

    private static final TipoDocumento TIPO_DOCUMENTO = TipoDocumento.BOLETO_BALCAO;
    private final IBoletoReports boletoReports;

    private final IBancoCupomClient cupomCapaService;

    public BoletoBalcaoDocumento(BoletoImpressaoMapper boletoImpressaoMapper,
                                 IBoletoReports boletoReports,
                                 IBancoCupomClient cupomCapaService) {
        super(boletoImpressaoMapper);
        this.boletoReports = boletoReports;
        this.cupomCapaService = cupomCapaService;
    }

    @Override
    protected TipoDocumento pegaTipoDocumento() {
        return TIPO_DOCUMENTO;
    }

    @Override
    protected void executaOperacaoDeImpressao(DocumentoItMarket boletoItMarket) {
        this.buscarInformacoesAdicionais(boletoItMarket);
        boletoReports.imprimirBoletoBalcao(boletoItMarket);
    }

    private void buscarInformacoesAdicionais(DocumentoItMarket boletoItMarket) {

        if (Objects.isNull(boletoItMarket.getIdPedido()) || Objects.isNull(boletoItMarket.getDataMovimento())) {

            CupomCapaDTO cupomCapaDTO = cupomCapaService.buscarCupomCapa(boletoItMarket.getFilialId(),
                    boletoItMarket.getPdv(), boletoItMarket.getCupom());

            final String recurso = "service=/boletoservice/ImpressaoBoletoService::imprimirBoletosBalcao::validarBoletoBalcao";

            if (Objects.isNull(cupomCapaDTO))
                throw new PdvValidationException("Cupom pendente de integração ou não encontrado: " + recurso);

            boletoItMarket.setIdPedido(cupomCapaDTO.getPedidoFaturado());
            boletoItMarket.setDataMovimento(cupomCapaDTO.getDataMovimento().toLocalDate());
        }
    }
}
