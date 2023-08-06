package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;
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

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

public class PromissoriaDocumentoTest {

    @Mock
    private BoletoImpressaoMapper boletoImpressaoMapper;
    @Mock
    private IBoletoReports boletoReports;
    @InjectMocks
    private PromissoriaDocumento promissoriaDocumento;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveExecutaProcessamento()  {
        boolean result = promissoriaDocumento.executaProcessamento(TipoDocumento.PROMISSORIA);
        Assert.assertTrue(result);
    }

    @Test
    public void deveImprimirBoletoPromissoria() {

        when(boletoImpressaoMapper.buscarBoletosPedentesDeImpressao())
                .thenReturn(Collections.singletonList(BoletoBuilder.promissoriaPendente()));

        promissoriaDocumento.imprime(Collections.singletonList(BoletoBuilder.promissoriaPendente()));

        verify(boletoReports, times(1)).imprimirPromissoria(BoletoBuilder.promissoriaPendente());
        verify(boletoImpressaoMapper, times(1)).atualizarBoletoItMarket(Mockito.any());

    }

    @Test
    public void deveRegistrarIncidenciaParaFalhaNaComunicaaoDoGmreportsParaPromissoria() throws IllegalAccessException {

        List<DocumentoItMarket> boletos = Collections.singletonList(BoletoBuilder.promissoriaPendente());

        when(boletoImpressaoMapper.buscarBoletosPedentesDeImpressao())
                .thenReturn(boletos);

        willThrow(PdvValidationException.class).given(boletoReports)
                .imprimirPromissoria(BoletoBuilder.promissoriaPendente());

        for (int count = 0; count < 15; count++) {
            promissoriaDocumento.imprime(boletos);
        }

        for (DocumentoItMarket documentoItMarket : boletos) {

            assertEquals(15, documentoItMarket.getIncidencia());

            assertEquals(TipoStatusImpressao.IMPRESSAO_COM_ERRO,
                    TipoStatusImpressao.toEnum(documentoItMarket.getTipoStatusImpressao()));

        }

    }

}