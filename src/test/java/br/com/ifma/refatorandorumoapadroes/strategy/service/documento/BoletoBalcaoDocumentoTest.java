package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import static org.mockito.Mockito.*;

public class BoletoBalcaoDocumentoTest {
    @Mock
    br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper boletoImpressaoMapper;
    @Mock
    br.com.ifma.refatorandorumoapadroes.strategy.client.IBoletoReports boletoReports;
    @Mock
    br.com.ifma.refatorandorumoapadroes.strategy.client.IBancoCupomClient cupomCapaService;
    @Mock
    org.slf4j.Logger log;
    @InjectMocks
    br.com.ifma.refatorandorumoapadroes.strategy.service.documento.BoletoBalcaoDocumento boletoBalcaoDocumento;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecutaProcessamento() throws Exception {
        boolean result = boletoBalcaoDocumento.executaProcessamento(br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto.BOLETO_LOJA);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testImprime() throws Exception {
        when(cupomCapaService.buscarCupomCapa(anyLong(), anyInt(), anyLong())).thenReturn(new br.com.ifma.refatorandorumoapadroes.strategy.model.CupomCapaDTO(Long.valueOf(1), Long.valueOf(1), Integer.valueOf(0), Long.valueOf(1), java.time.LocalDateTime.of(2023, java.time.Month.MARCH, 1, 3, 11, 28), new java.math.BigDecimal(0), Boolean.TRUE, Boolean.TRUE, java.time.LocalDateTime.of(2023, java.time.Month.MARCH, 1, 3, 11, 28), Boolean.TRUE, Long.valueOf(1)));

        boletoBalcaoDocumento.imprime(java.util.Arrays.<br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket>asList(new br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket(Long.valueOf(1), Long.valueOf(1), Integer.valueOf(0), Long.valueOf(1), Integer.valueOf(0), new java.math.BigDecimal(0), "cpfOuCnpj", "dataDocumento", "dataVencimento", Long.valueOf(1), Long.valueOf(1), Long.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Long.valueOf(1), 0, java.time.LocalDate.of(2023, java.time.Month.MARCH, 1), Long.valueOf(1))));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme