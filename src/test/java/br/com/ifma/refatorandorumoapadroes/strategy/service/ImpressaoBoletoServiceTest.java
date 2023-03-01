package br.com.ifma.refatorandorumoapadroes.strategy.service;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
import br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.service.builder.BoletoBuilder;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.BoletoBalcaoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.BoletoLojaDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.CarneDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.PromissoriaDocumento;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    @Mock
    private CarneDocumento carneDocumento;

    @Mock
    private PromissoriaDocumento promissoriaDocumento;

    @Mock
    private BoletoBalcaoDocumento boletoBalcaoDocumento;

    @InjectMocks
    private ImpressaoBoletoService impressaoBoletoService;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveImprimirBoletos() {

        when(boletoImpressaoMapper.buscarBoletosPedentesDeImpressao())
                .thenReturn(BoletoBuilder.pegaBoletos());


        impressaoBoletoService.imprimirBoletos();


        verify(boletoLojaDocumento, times(1)).imprime(any());
        verify(carneDocumento, times(1)).imprime(any());
        verify(promissoriaDocumento, times(1)).imprime(any());
        verify(boletoBalcaoDocumento, times(1)).imprime(any());

    }





}

