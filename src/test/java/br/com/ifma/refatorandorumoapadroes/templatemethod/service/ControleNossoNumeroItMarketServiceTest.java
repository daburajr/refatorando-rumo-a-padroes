package br.com.ifma.refatorandorumoapadroes.templatemethod.service;

import br.com.ifma.refatorandorumoapadroes.templatemethod.mapper.BoletoNossoNumeroItMarketMapper;
import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumeroItMarket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ControleNossoNumeroItMarketServiceTest {

    @Mock
    private BoletoNossoNumeroItMarketMapper boletoNossoNumeroItMarketMapper;

    @InjectMocks
    private ControleNossoNumeroItMarketService controleNossoNumeroItMarketService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveGerarControleNossoNumero() {

        BoletoNossoNumeroItMarket result = (BoletoNossoNumeroItMarket) controleNossoNumeroItMarketService.gerarControleNossoNumero(1L, 600, 1235L);

        verify(boletoNossoNumeroItMarketMapper, times(1)).inserirBoletoNossoNuemroItMarket(any());

        Assert.assertNotNull(result);
    }
}

