package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import br.com.ifma.refatorandorumoapadroes.simplefactory.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.ContaMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.Conta;
import br.com.ifma.refatorandorumoapadroes.simplefactory.service.builder.ContaBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
    public void recuperarContaPorId() {

        Conta conta = ContaBuilder.criaContaBB();

        when(contaMapper.recuperarContaPorId(conta.getId())).thenReturn(conta);

        contaService.recuperarContaPorId(1L);

        verify(contaMapper, times(1)).recuperarContaPorId(1L);


    }

    @Test(expected = PdvValidationException.class)
    public void naoDeveRecuperarContaPorId() {

        Conta conta = ContaBuilder.criaContaBB();

        when(contaMapper.recuperarContaPorId(conta.getId())).thenReturn(conta);

        contaService.recuperarContaPorId(any());
    }

    @Test
    public void recuperarIdBancoPeloIdConta() {

        Conta conta = ContaBuilder.criaContaBB();

        when(contaMapper.recuperarIdBancoPeloIdConta(conta.getId())).thenReturn(2L);

        long result = contaService.recuperarIdBancoPeloIdConta(conta.getId());

        assertEquals(2L, result);
        verify(contaMapper, times(1)).recuperarIdBancoPeloIdConta(1L);

    }

    @Test(expected = PdvValidationException.class)
    public void naoDeveRecuperarIdBancoPeloIdConta() {

        Conta conta = ContaBuilder.criaContaBB();

        when(contaMapper.recuperarIdBancoPeloIdConta(conta.getId())).thenReturn(2L);

        contaService.recuperarIdBancoPeloIdConta(any());

    }

    @Test
    public void recuperarCodigoBeneficiarioPelaConta() {

        Conta conta = ContaBuilder.criaContaBB();

        when(contaMapper.recuperarCodigoBeneficiarioPelaConta(conta.getId())).thenReturn("cod_bene");

        String result = contaService.recuperarCodigoBeneficiarioPelaConta(conta.getId());

        assertEquals("cod_bene", result);
        verify(contaMapper, times(1)).recuperarCodigoBeneficiarioPelaConta(1L);

    }

    @Test(expected = PdvValidationException.class)
    public void naoDeveRecuperarCodigoBeneficiarioPelaConta() {

        Conta conta = ContaBuilder.criaContaBB();

        when(contaMapper.recuperarCodigoBeneficiarioPelaConta(conta.getId())).thenReturn("");

        contaService.recuperarCodigoBeneficiarioPelaConta(conta.getId());

    }



}