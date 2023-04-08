package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import br.com.ifma.refatorandorumoapadroes.simplefactory.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.ContaMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.Conta;
import br.com.ifma.refatorandorumoapadroes.simplefactory.service.builder.ContaBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ContaServiceTest {

    @Mock
    private ContaMapper contaMapper;
    @InjectMocks
    private ContaService contaService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveRecuperarContaPorId() {

        Conta conta = ContaBuilder.criaContaBB();

        when(contaMapper.recuperarContaPorId(anyLong())).thenReturn(conta);

        Conta result = contaService.recuperarContaPorId(0L);
        Assert.assertEquals(conta, result);

    }

    @Test
    public void deveRecuperarIdBancoPeloIdConta() {
        when(contaMapper.recuperarIdBancoPeloIdConta(anyLong())).thenReturn(2L);
        long result = contaService.recuperarIdBancoPeloIdConta(anyLong());
        Assert.assertEquals(2L, result);
    }

    @Test
    public void naoDeveLancarExceptionAoValidaCodigoDoBeneficiario() {
        when(contaMapper.recuperarCodigoBeneficiarioPelaConta(anyLong())).thenReturn("181817");
        contaService.validaCodigoDoBeneficiario(0L);
        verify(contaMapper, times(1)).recuperarCodigoBeneficiarioPelaConta(anyLong());
    }

    @Test(expected = PdvValidationException.class)
    public void naoDeveRecuperarIdBancoPeloIdConta() {
        when(contaMapper.recuperarIdBancoPeloIdConta(anyLong())).thenReturn(0L);
        contaService.recuperarIdBancoPeloIdConta(anyLong());
    }

    @Test(expected = PdvValidationException.class)
    public void deveLancarExceptionAoValidaCodigoDoBeneficiario() {
        when(contaMapper.recuperarCodigoBeneficiarioPelaConta(anyLong())).thenReturn("");
        contaService.validaCodigoDoBeneficiario(0L);
    }


}

