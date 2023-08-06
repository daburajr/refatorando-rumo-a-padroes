package br.com.ifma.refatorandorumoapadroes.strategy.service;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import br.com.ifma.refatorandorumoapadroes.strategy.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;
import br.com.ifma.refatorandorumoapadroes.strategy.service.builder.BoletoBuilder;
import br.com.ifma.refatorandorumoapadroes.strategy.service.builder.CupomCapaBuilder;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.BoletoLojaDocumento;
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

public class ImpressaoBoletoServiceTest {
    @Mock
    private BoletoImpressaoMapper boletoImpressaoMapper;

    @Mock
    private IBoletoReports boletoReports;

    @Mock
    private IBancoCupomClient cupomCapaService;

    @Mock
    private BoletoLojaDocumento boletoLojaDocumento;

    @InjectMocks
    private ImpressaoBoletoService impressaoBoletoService;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveimprimirBoletos() {

        when(boletoImpressaoMapper.buscarBoletosPedentesDeImpressao())
                .thenReturn(BoletoBuilder.pegaBoletos());

        when(cupomCapaService.buscarCupomCapa(1L, 700, 3570L))
                .thenReturn(CupomCapaBuilder.cupomCapaDTO());

        impressaoBoletoService.imprimirBoletos();

        verify(boletoReports, times(1)).imprimirBoletoBalcao(BoletoBuilder.boletoBalcaoPendente());
        verify(boletoReports, times(1)).imprimirCarne(BoletoBuilder.carnePendente());
        verify(boletoReports, times(1)).imprimirPromissoria(BoletoBuilder.promissoriaPendente());

        verify(boletoImpressaoMapper, times(3)).atualizarBoletoItMarket(Mockito.any());
        verify(boletoLojaDocumento, times(1)).imprime(any());

    }

    @Test
    public void deveRegistrarIncidenciaParaFalhaNaComunicaaoDoGmreports() throws IllegalAccessException {

        List<BoletoItMarket> boletos = BoletoBuilder.pegaBoletosSemBoletosLoja();

        when(boletoImpressaoMapper.buscarBoletosPedentesDeImpressao())
                .thenReturn(boletos);

        when(cupomCapaService.buscarCupomCapa(1L, 700, 3570L))
                .thenReturn(CupomCapaBuilder.cupomCapaDTO());

        willThrow(PdvValidationException.class).given(boletoReports).imprimirBoletoBalcao(BoletoBuilder.boletoBalcaoPendente());
        willThrow(PdvValidationException.class).given(boletoReports).imprimirCarne(BoletoBuilder.carnePendente());
        willThrow(PdvValidationException.class).given(boletoReports).imprimirPromissoria(BoletoBuilder.promissoriaPendente());


        for (int count = 0; count < 15; count++) {
            impressaoBoletoService.imprimirBoletos();
        }

        for (BoletoItMarket boletoItMarket : boletos) {
            assertEquals(15, boletoItMarket.getIncidencia());
            assertEquals(TipoStatusImpressao.IMPRESSAO_COM_ERRO, TipoStatusImpressao.toEnum(boletoItMarket.getTipoStatusImpressao()));
        }


    }

}

