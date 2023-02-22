package br.com.ifma.refatorandorumoapadroes.templatemethod.service;

import br.com.ifma.refatorandorumoapadroes.templatemethod.mapper.BoletoNossoNumeroVMixMapper;
import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumeroItMarket;
import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumeroVMix;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.*;

public class ControleNossoNumeroVmixServiceTest {
    @Mock
    BoletoNossoNumeroVMixMapper boletoNossoNumeroVMixMapper;
    @InjectMocks
    ControleNossoNumeroVmixService controleNossoNumeroVmixService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGerarControleNossoNumero() {

        BoletoNossoNumeroItMarket boletoNossoNumeroItMarket = BoletoNossoNumeroItMarket.builder()
                .id(1L)
                .nossoNumero(1235L)
                .status(1)
                .idFilial(1L)
                .pdv(600)
                .data(new GregorianCalendar(2023, Calendar.FEBRUARY, 20, 0, 32).getTime())
                .build();

        BoletoNossoNumeroVMix result = controleNossoNumeroVmixService.gerarControleNossoNumero(boletoNossoNumeroItMarket);

        verify(boletoNossoNumeroVMixMapper, times(1)).inserirBoletoNossoNumeroVMix(any());

        Assert.assertNotNull(result);
    }
}

