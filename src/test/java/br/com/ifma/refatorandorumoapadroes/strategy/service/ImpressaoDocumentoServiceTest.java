package br.com.ifma.refatorandorumoapadroes.strategy.service;

import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.service.builder.BoletoBuilder;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ImpressaoDocumentoServiceTest {

    @Mock
    private BoletoImpressaoMapper boletoImpressaoMapper;

    @Mock
    private BoletoLojaDocumento boletoLojaDocumento;

    @Mock
    private CarneDocumento carneDocumento;

    @Mock
    private PromissoriaDocumento promissoriaDocumento;

    @Mock
    private BoletoBalcaoDocumento boletoBalcaoDocumento;

    @Spy
    private List<Documento> processaDocumentos = new ArrayList<>();

    @InjectMocks
    private ImpressaoDocumentoService impressaoDocumentoService;

    @Before
    public void setUp() {

        MockitoAnnotations.openMocks(this);

        processaDocumentos.add(carneDocumento);
        processaDocumentos.add(boletoLojaDocumento);
        processaDocumentos.add(promissoriaDocumento);
        processaDocumentos.add(boletoBalcaoDocumento);
    }

    @Test
    public void deveimprimirBoletos() {

        when(boletoImpressaoMapper.buscarBoletosPedentesDeImpressao())
                .thenReturn(BoletoBuilder.pegaBoletos());

        when(carneDocumento.executaProcessamento(TipoDocumento.CARNE)).thenReturn(true);
        when(boletoLojaDocumento.executaProcessamento(TipoDocumento.BOLETO_LOJA)).thenReturn(true);
        when(promissoriaDocumento.executaProcessamento(TipoDocumento.PROMISSORIA)).thenReturn(false);
        when(boletoBalcaoDocumento.executaProcessamento(TipoDocumento.BOLETO_BALCAO)).thenReturn(false);

        impressaoDocumentoService.imprimirBoletos();

        verify(boletoLojaDocumento, times(1)).imprime(any());
        verify(carneDocumento, times(1)).imprime(any());
        verify(promissoriaDocumento, times(0)).imprime(any());
        verify(boletoBalcaoDocumento, times(0)).imprime(any());

    }


}

