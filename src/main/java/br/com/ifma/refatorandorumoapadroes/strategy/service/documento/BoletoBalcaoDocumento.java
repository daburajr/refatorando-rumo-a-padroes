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

@Slf4j
@Service
@RequiredArgsConstructor
public class BoletoBalcaoDocumento implements Documento {

    private static final TipoDocumento TIPO_DOCUMENTO = TipoDocumento.BOLETO_BALCAO;

    private static final Integer INCIDENCIA = 15;

    private final BoletoImpressaoMapper boletoImpressaoMapper;

    private final IBoletoReports boletoReports;

    private final IBancoCupomClient cupomCapaService;

    @Override
    public boolean executaProcessamento(TipoDocumento tipo) {
        return TIPO_DOCUMENTO.equals(tipo);
    }

    @Override
    public void imprime(List<DocumentoItMarket> documentos) {
        documentos.forEach( boletoItMarket -> {
            try {
                this.buscarInformacoesAdicionais(boletoItMarket);
                boletoReports.imprimirBoletoBalcao(boletoItMarket);
                this.atualizarBoletoItMarket(boletoItMarket, TipoStatusImpressao.IMPRESSAO_CONCLUIDA);
            } catch (Exception e) {
                this.registrarIncidenciaEError(boletoItMarket, e.getMessage());
            }
        });
    }

    private void atualizarBoletoItMarket(DocumentoItMarket boletoItMarket, TipoStatusImpressao statusImpressao) {
        boletoItMarket.setTipoStatusImpressao(statusImpressao.getCodigo());
        boletoImpressaoMapper.atualizarBoletoItMarket(boletoItMarket);
    }

    private void registrarIncidenciaEError(DocumentoItMarket boletoItMarket, String mensagemDeErro) {

        boletoItMarket.adicionaIncidencia();

        if (boletoItMarket.getIncidencia() >= INCIDENCIA) {
            this.atualizarBoletoItMarket(boletoItMarket, TipoStatusImpressao.IMPRESSAO_COM_ERRO);
            boletoImpressaoMapper.registrarError(boletoItMarket, mensagemDeErro);
        }

        boletoImpressaoMapper.atualizarBoletoItMarket(boletoItMarket);
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
