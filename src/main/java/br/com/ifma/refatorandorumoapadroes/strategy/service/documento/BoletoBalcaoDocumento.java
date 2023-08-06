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

import static br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento.BOLETO_BALCAO;

@Slf4j
@Service
public class BoletoBalcaoDocumento extends TemplateDocumento {

    protected BoletoBalcaoDocumento(BoletoImpressaoMapper boletoImpressaoMapper,
                                    IBoletoReports boletoReports,
                                    IBancoCupomClient cupomCapaService) {
        super(boletoImpressaoMapper, boletoReports, cupomCapaService);
    }

    @Override
    protected TipoDocumento pegaTipoDocumento() {
        return BOLETO_BALCAO;
    }

    @Override
    protected void executaOperacaoDeImpressao(DocumentoItMarket documentoItMarket) {
        this.buscarInformacoesAdicionais(documentoItMarket);
        boletoReports.imprimirBoletoBalcao(documentoItMarket);
    }

    private void buscarInformacoesAdicionais(DocumentoItMarket documentoItMarket) {

        if (Objects.isNull(documentoItMarket.getIdPedido()) || Objects.isNull(documentoItMarket.getDataMovimento())) {

            CupomCapaDTO cupomCapaDTO = cupomCapaService.buscarCupomCapa(documentoItMarket.getFilialId(),
                    documentoItMarket.getPdv(), documentoItMarket.getCupom());

            final String recurso = "service=/boletoservice/ImpressaoBoletoService::imprimirBoletosBalcao::validarBoletoBalcao";

            if (Objects.isNull(cupomCapaDTO))
                throw new PdvValidationException("Cupom pendente de integração ou não encontrado: " + recurso);

            documentoItMarket.setIdPedido(cupomCapaDTO.getPedidoFaturado());
            documentoItMarket.setDataMovimento(cupomCapaDTO.getDataMovimento().toLocalDate());
        }
    }
}
