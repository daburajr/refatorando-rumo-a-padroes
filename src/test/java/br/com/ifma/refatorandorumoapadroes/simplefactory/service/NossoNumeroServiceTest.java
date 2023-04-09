package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import br.com.ifma.refatorandorumoapadroes.simplefactory.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.ContaMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.NossoNumeroMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.Conta;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;
import br.com.ifma.refatorandorumoapadroes.simplefactory.service.builder.ContaBuilder;
import br.com.ifma.refatorandorumoapadroes.simplefactory.service.builder.InformacaoNossoNumeroBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NossoNumeroServiceTest {

    @Mock
    private NossoNumeroMapper nossoNumeroMapper;

    @Mock
    private ContaService contaService;

    @Mock
    private FabricaDeInformacaoNossoNumeroService fabrica;

    @InjectMocks
    private NossoNumeroService nossoNumeroService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveCriarNossoNumeroLojaParaBB() {

        Conta conta = ContaBuilder.criaContaBB();

        List<InformacoesNossoNumero> expected = Collections
                .singletonList(InformacaoNossoNumeroBuilder.criaInformacaoNossoNumeroBB());

        when(nossoNumeroMapper.recuperarIdContaFilial(1L)).thenReturn(conta.getId());
        when(contaService.recuperarContaPorId(conta.getId())).thenReturn(conta);
        when(contaService.recuperarIdBancoPeloIdConta(conta.getId())).thenReturn(1L);

        doNothing().when(contaService).validaCodigoDoBeneficiario(anyLong());

        when(fabrica.criaInformacaoNossoNumeroParaBrasil(anyLong(), any(), anyLong()))
                .thenReturn(InformacaoNossoNumeroBuilder.criaInformacaoNossoNumeroBB());

        when(nossoNumeroMapper.gerarNossoNumeroProcedure(anyLong(), anyInt(),
                anyLong(), anyInt(), any(), anyLong())).thenReturn(15L);

        List<InformacoesNossoNumero> nossoNumeros = nossoNumeroService
                .criarNossoNumeroLoja(1L, 700, 3570L, 1);

        assertEquals(expected, nossoNumeros);

    }

    @Test
    public void deveCriarNossoNumeroLojaParaSantander() {

        Conta conta = ContaBuilder.criaContaSantander();

        List<InformacoesNossoNumero> expected = Collections
                .singletonList(InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroSantander());

        when(nossoNumeroMapper.recuperarIdContaFilial(1L)).thenReturn(conta.getId());
        when(contaService.recuperarContaPorId(conta.getId())).thenReturn(conta);
        when(contaService.recuperarIdBancoPeloIdConta(conta.getId())).thenReturn(33L);
        doNothing().when(contaService).validaCodigoDoBeneficiario(anyLong());

        when(nossoNumeroMapper.gerarNossoNumeroProcedure(anyLong(), anyInt(),
                anyLong(), anyInt(), any(), anyLong())).thenReturn(15L);

        when(fabrica.criaInformacaoNossoNumeroParaSantander(anyLong(), any(), anyLong()))
                .thenReturn(InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroSantander());

        List<InformacoesNossoNumero> nossoNumeros = nossoNumeroService
                .criarNossoNumeroLoja(1L, 700, 3570L, 1);

        assertEquals(expected, nossoNumeros);

        verify(contaService, times(0)).validaCodigoDoBeneficiario(anyLong());

    }

    @Test
    public void deveCriarNossoNumeroLojaParaBradesco() {

        Conta conta = ContaBuilder.criaContaBradesco();

        List<InformacoesNossoNumero> expected = Collections
                .singletonList(InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroBradesco());

        when(nossoNumeroMapper.recuperarIdContaFilial(1L)).thenReturn(conta.getId());
        when(contaService.recuperarContaPorId(conta.getId())).thenReturn(conta);
        when(contaService.recuperarIdBancoPeloIdConta(conta.getId())).thenReturn(237L);

        when(nossoNumeroMapper.gerarNossoNumeroProcedure(anyLong(), anyInt(),
                anyLong(), anyInt(), any(), anyLong())).thenReturn(15L);

        when(fabrica.criaInformacaoNossoNumeroParaBradesco(anyLong(), any(), anyLong()))
                .thenReturn(InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroBradesco());

        List<InformacoesNossoNumero> nossoNumeros = nossoNumeroService
                .criarNossoNumeroLoja(1L, 700, 3570L, 1);

        assertEquals(expected, nossoNumeros);

        verify(contaService, times(0)).validaCodigoDoBeneficiario(anyLong());

    }

    @Test
    public void deveCriarNossoNumeroLojaParaSafra() {

        Conta conta = ContaBuilder.criaContaSafra();

        List<InformacoesNossoNumero> expected = Collections
                .singletonList(InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroSafra());

        when(nossoNumeroMapper.recuperarIdContaFilial(1L)).thenReturn(conta.getId());
        when(contaService.recuperarContaPorId(conta.getId())).thenReturn(conta);
        when(contaService.recuperarIdBancoPeloIdConta(conta.getId())).thenReturn(422L);

        when(nossoNumeroMapper.gerarNossoNumeroProcedure(anyLong(), anyInt(),
                anyLong(), anyInt(), any(), anyLong())).thenReturn(15L);

        when(fabrica.criaInformacaoNossoNumeroParaSafra(anyLong(), any(), anyLong()))
                .thenReturn(InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroSafra());

        List<InformacoesNossoNumero> nossoNumeros = nossoNumeroService
                .criarNossoNumeroLoja(1L, 700, 3570L, 1);

        assertEquals(expected, nossoNumeros);

        verify(contaService, times(0)).validaCodigoDoBeneficiario(anyLong());

    }

    @Test(expected = PdvValidationException.class)
    public void naoDeveCriarNossoNumeroLojaParaIdContaNula() {
        when(nossoNumeroMapper.recuperarIdContaFilial(1L)).thenReturn(null);
        nossoNumeroService.criarNossoNumeroLoja(1L, 700, 3570L, 1);
    }

    @Test(expected = PdvValidationException.class)
    public void naoDeveCriarNossoNumeroLojaParaContaNula() {

        Conta conta = ContaBuilder.criaContaBB();

        when(nossoNumeroMapper.recuperarIdContaFilial(1L)).thenReturn(conta.getId());
        when(contaService.recuperarContaPorId(anyLong())).thenThrow(PdvValidationException.class);

        nossoNumeroService.criarNossoNumeroLoja(1L, 700, 3570L, 1);

    }

    @Test(expected = PdvValidationException.class)
    public void naoDeveCriarNossoNumeroLojaParaIdBancoNulo() {

        Conta conta = ContaBuilder.criaContaSafra();

        when(nossoNumeroMapper.recuperarIdContaFilial(1L)).thenReturn(conta.getId());
        when(contaService.recuperarContaPorId(conta.getId())).thenReturn(conta);
        when(contaService.recuperarIdBancoPeloIdConta(conta.getId())).thenThrow(PdvValidationException.class);

        when(nossoNumeroMapper.gerarNossoNumeroProcedure(anyLong(), anyInt(),
                anyLong(), anyInt(), any(), anyLong())).thenReturn(15L);

        nossoNumeroService.criarNossoNumeroLoja(1L, 700, 3570L, 1);

    }

    @Test(expected = PdvValidationException.class)
    public void naoDeveCriarNossoNumeroLojaParaBBParaCodigoBeneficiarioNulo() {

        Conta conta = ContaBuilder.criaContaBB();

        when(nossoNumeroMapper.recuperarIdContaFilial(1L)).thenReturn(conta.getId());
        when(contaService.recuperarContaPorId(conta.getId())).thenReturn(conta);
        when(contaService.recuperarIdBancoPeloIdConta(conta.getId())).thenReturn(1L);
        doThrow(PdvValidationException.class).when(contaService).validaCodigoDoBeneficiario(anyLong());

        when(nossoNumeroMapper.gerarNossoNumeroProcedure(anyLong(), anyInt(),
                anyLong(), anyInt(), any(), anyLong())).thenReturn(15L);

        nossoNumeroService.criarNossoNumeroLoja(1L, 700, 3570L, 1);

    }


}

