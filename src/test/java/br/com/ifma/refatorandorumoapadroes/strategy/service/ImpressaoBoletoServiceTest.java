package br.com.ifma.refatorandorumoapadroes.strategy.service;

import br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.service.builder.BoletoBuilder;
import br.com.ifma.refatorandorumoapadroes.strategy.service.builder.CupomCapaBuilder;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.BoletoBalcaoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.BoletoLojaDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.CarneDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.PromissoriaDocumento;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class ImpressaoBoletoServiceTest {

    @Mock
    private BoletoImpressaoMapper boletoImpressaoMapper;

    @Mock
    private IBancoCupomClient cupomCapaService;

    @Mock
    private BoletoLojaDocumento boletoLojaDocumento;

    @Mock
    private BoletoBalcaoDocumento boletoBalcaoDocumento;

    @Mock
    private CarneDocumento carneDocumento;

    @Mock
    private PromissoriaDocumento promissoriaDocumento;

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

        verify(boletoLojaDocumento, times(1)).imprime(Collections.singletonList(BoletoBuilder.boletoLojaPendente()));
        verify(boletoBalcaoDocumento, times(1)).imprime(Collections.singletonList(BoletoBuilder.boletoBalcaoPendente()));
        verify(carneDocumento, times(1)).imprime(Collections.singletonList(BoletoBuilder.carnePendente()));
        verify(promissoriaDocumento, times(1)).imprime(Collections.singletonList(BoletoBuilder.promissoriaPendente()));

    }


}

