package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;
import br.com.ifma.refatorandorumoapadroes.strategy.model.CupomCapaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento.BOLETO_BALCAO;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoletoBalcaoDocumento implements Documento {

    private final BoletoImpressaoMapper boletoImpressaoMapper;
    private final IBoletoReports boletoReports;
    private final IBancoCupomClient cupomCapaService;

    private static final Integer INCIDENCIA = 15;

    @Override
    public boolean executaProcessamento(TipoDocumento tipo) {
        return BOLETO_BALCAO.equals(tipo);
    }

    @Override
    public void imprime(List<DocumentoItMarket> documentos) {
        documentos.forEach(documentoItMarket -> {
            try {

                this.buscarInformacoesAdicionais(documentoItMarket);
                boletoReports.imprimirBoletoBalcao(documentoItMarket);

                this.atualizarBoletoItMarket(documentoItMarket,
                        TipoStatusImpressao.IMPRESSAO_CONCLUIDA);

            } catch (Exception e) {
                this.registrarIncidenciaEError(documentoItMarket, e.getMessage());
            }
        });
    }

    private void registrarIncidenciaEError(DocumentoItMarket documentoItMarket,
                                           String mensagemDeErro) {

        documentoItMarket.adicionaIncidencia();

        if (documentoItMarket.getIncidencia() >= INCIDENCIA) {

            this.atualizarBoletoItMarket(documentoItMarket,
                    TipoStatusImpressao.IMPRESSAO_COM_ERRO);

            boletoImpressaoMapper
                    .registrarError(documentoItMarket, mensagemDeErro);
        }

        boletoImpressaoMapper.atualizarBoletoItMarket(documentoItMarket);
    }

    private void atualizarBoletoItMarket(DocumentoItMarket documentoItMarket,
                                         TipoStatusImpressao statusImpressao) {
        documentoItMarket.setTipoStatusImpressao(statusImpressao.getCodigo());
        boletoImpressaoMapper.atualizarBoletoItMarket(documentoItMarket);
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
