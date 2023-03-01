package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;
import br.com.ifma.refatorandorumoapadroes.strategy.service.builder.BoletoBuilder;
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

public class CarneDocumentoTest {

    @Mock
    private BoletoImpressaoMapper boletoImpressaoMapper;
    @Mock
    private IBoletoReports boletoReports;
    @InjectMocks
    private CarneDocumento carneDocumento;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveExecutaProcessamento()  {
        boolean result = carneDocumento.executaProcessamento(TipoBoleto.CARNE);
        Assert.assertEquals(true, result);
    }

    @Test
    public void deveExecutarAImpressao() {

        List<BoletoItMarket> boletos = Collections.singletonList(BoletoBuilder.carnePendente());

        when(boletoImpressaoMapper.buscarBoletosPedentesDeImpressao())
                .thenReturn(boletos);

        carneDocumento.imprime(boletos);

        verify(boletoReports, times(1)).imprimirBoletoLoja(BoletoBuilder.carnePendente());
        verify(boletoImpressaoMapper, times(1)).atualizarBoletoItMarket(Mockito.any());
    }

    @Test
    public void deveRegistrarIncidenciaParaFalhaNaComunicaaoDoGmreports() throws IllegalAccessException {

        List<BoletoItMarket> boletos = Collections.singletonList(BoletoBuilder.carnePendente());

        when(boletoImpressaoMapper.buscarBoletosPedentesDeImpressao())
                .thenReturn(boletos);

        willThrow(PdvValidationException.class).given(boletoReports).imprimirBoletoLoja(BoletoBuilder.carnePendente());

        for (int count = 0; count < 15; count++) {
            carneDocumento.imprime(boletos);
        }

        for (BoletoItMarket boletoItMarket : boletos) {
            assertEquals(15, boletoItMarket.getIncidencia());
            assertEquals(TipoStatusImpressao.IMPRESSAO_COM_ERRO, TipoStatusImpressao.toEnum(boletoItMarket.getTipoStatusImpressao()));
        }

    }
}

