package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;
import br.com.ifma.refatorandorumoapadroes.strategy.model.CupomCapaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto.BOLETO_BALCAO;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoletoBalcaoDocumento implements Documento {

    private final BoletoImpressaoMapper boletoImpressaoMapper;
    private final IBoletoReports boletoReports;
    private final IBancoCupomClient cupomCapaService;

    private static final Integer INCIDENCIA = 15;

    @Override
    public boolean executaProcessamento(TipoBoleto tipo) {
        return BOLETO_BALCAO.equals(tipo);
    }

    @Override
    public void imprime(List<BoletoItMarket> documentos) {
        documentos.forEach(boletoItMarket -> {
            try {

                this.buscarInformacoesAdicionais(boletoItMarket);
                boletoReports.imprimirBoletoBalcao(boletoItMarket);

                this.atualizarBoletoItMarket(boletoItMarket,
                        TipoStatusImpressao.IMPRESSAO_CONCLUIDA);

            } catch (Exception e) {
                this.registrarIncidenciaEError(boletoItMarket, e.getMessage());
            }
        });
    }

    private void registrarIncidenciaEError(BoletoItMarket boletoItMarket,
                                           String mensagemDeErro) {

        boletoItMarket.adicionaIncidencia();

        if (boletoItMarket.getIncidencia() >= INCIDENCIA) {

            this.atualizarBoletoItMarket(boletoItMarket,
                    TipoStatusImpressao.IMPRESSAO_COM_ERRO);

            boletoImpressaoMapper
                    .registrarError(boletoItMarket, mensagemDeErro);
        }

        boletoImpressaoMapper.atualizarBoletoItMarket(boletoItMarket);
    }

    private void atualizarBoletoItMarket(BoletoItMarket boletoItMarket,
                                         TipoStatusImpressao statusImpressao) {
        boletoItMarket.setTipoStatusImpressao(statusImpressao.getCodigo());
        boletoImpressaoMapper.atualizarBoletoItMarket(boletoItMarket);
    }

    private void buscarInformacoesAdicionais(BoletoItMarket boletoItMarket) {

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
