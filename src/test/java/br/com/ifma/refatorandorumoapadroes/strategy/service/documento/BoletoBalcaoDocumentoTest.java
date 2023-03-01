package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;
import br.com.ifma.refatorandorumoapadroes.strategy.service.builder.BoletoBuilder;
import br.com.ifma.refatorandorumoapadroes.strategy.service.builder.CupomCapaBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

public class BoletoBalcaoDocumentoTest {
    @Mock
    private BoletoImpressaoMapper boletoImpressaoMapper;
    @Mock
    private IBoletoReports boletoReports;
    @Mock
    private IBancoCupomClient cupomCapaService;
    @InjectMocks
    private BoletoBalcaoDocumento boletoBalcaoDocumento;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveExecutaProcessamento() {
        boolean result = boletoBalcaoDocumento.executaProcessamento(TipoBoleto.BOLETO_BALCAO);
        Assert.assertTrue(result);
    }

    @Test
    public void deveExecutarAImpressao() {

        List<BoletoItMarket> boletos = Collections.singletonList(BoletoBuilder.boletoBalcaoPendente());

        when(cupomCapaService.buscarCupomCapa(1L, 700, 3570L))
                .thenReturn(CupomCapaBuilder.cupomCapaDTO());

        when(boletoImpressaoMapper.buscarBoletosPedentesDeImpressao())
                .thenReturn(boletos);

        boletoBalcaoDocumento.imprime(boletos);

        verify(boletoReports, times(1)).imprimirBoletoBalcao(BoletoBuilder.boletoBalcaoPendente());
        verify(boletoImpressaoMapper, times(1)).atualizarBoletoItMarket(Mockito.any());
    }

    @Test
    public void deveRegistrarIncidenciaParaFalhaNaComunicaaoDoGmreports() throws IllegalAccessException {

        List<BoletoItMarket> boletos = Collections.singletonList(BoletoBuilder.boletoBalcaoPendente());

        when(boletoImpressaoMapper.buscarBoletosPedentesDeImpressao())
                .thenReturn(boletos);

        willThrow(PdvValidationException.class).given(boletoReports).imprimirBoletoBalcao(BoletoBuilder.carnePendente());

        for (int count = 0; count < 15; count++) {
            boletoBalcaoDocumento.imprime(boletos);
        }

        for (BoletoItMarket boletoItMarket : boletos) {
            assertEquals(15, boletoItMarket.getIncidencia());
            assertEquals(TipoStatusImpressao.IMPRESSAO_COM_ERRO, TipoStatusImpressao.toEnum(boletoItMarket.getTipoStatusImpressao()));
        }

        verify(boletoImpressaoMapper, times(1)).registrarError(any(), anyString());


    }

}

