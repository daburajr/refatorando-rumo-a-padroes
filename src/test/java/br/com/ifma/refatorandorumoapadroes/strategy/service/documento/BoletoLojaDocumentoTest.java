package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
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

public class BoletoLojaDocumentoTest {

    @Mock
    private BoletoImpressaoMapper boletoImpressaoMapper;

    @Mock
    private IBoletoReports boletoReports;

    @Mock
    private IBancoCupomClient cupomCapaService;

    @InjectMocks
    private BoletoLojaDocumento boletoLojaDocumento;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveExecutaProcessamento()  {
        boolean result = boletoLojaDocumento.executaProcessamento(TipoDocumento.BOLETO_LOJA);
        Assert.assertTrue(result);
    }

    @Test
    public void deveImprimirBoletoLoja() {

        when(boletoImpressaoMapper.buscarBoletosPedentesDeImpressao())
                .thenReturn(Collections.singletonList(BoletoBuilder.boletoLojaPendente()));

        boletoLojaDocumento.imprime(Collections.singletonList(BoletoBuilder.boletoLojaPendente()));

        verify(boletoReports, times(1)).imprimirBoletoLoja(BoletoBuilder.boletoLojaPendente());
        verify(boletoImpressaoMapper, times(1)).atualizarBoletoItMarket(Mockito.any());

    }

    @Test
    public void deveRegistrarIncidenciaParaFalhaNaComunicaaoDoGmreportsParaBoletoLoja() throws IllegalAccessException {

        List<DocumentoItMarket> boletos = Collections.singletonList(BoletoBuilder.boletoLojaPendente());

        when(boletoImpressaoMapper.buscarBoletosPedentesDeImpressao())
                .thenReturn(boletos);

        willThrow(PdvValidationException.class).given(boletoReports).imprimirBoletoLoja(BoletoBuilder.boletoBalcaoPendente());

        for (int count = 0; count < 15; count++) {
            boletoLojaDocumento.imprime(boletos);
        }

        for (DocumentoItMarket documentoItMarket : boletos) {
            assertEquals(15, documentoItMarket.getIncidencia());
            assertEquals(TipoStatusImpressao.IMPRESSAO_COM_ERRO, TipoStatusImpressao.toEnum(documentoItMarket.getTipoStatusImpressao()));
        }

    }
}