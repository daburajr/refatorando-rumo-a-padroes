package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class CalculoDigitoVerificadorServiceTest {


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void calcularDigitoModulo11CnabComBaseParaBradesco() {

        String digito = CalculoDigitoVerificadorService
                .calcularDigitoModulo11CnabComBase("1100000000015", 7);

        Assert.assertEquals("0", digito);

    }

    @Test
    public void retornarPadraoParaBradesco() {

        String padrao = CalculoDigitoVerificadorService
                .retornarPadrao(13, 2, 7, 2, CalculoDigitoVerificadorService.Ordem.DireitaEsquerda);

        Assert.assertEquals("2765432765432", padrao);
    }

    @Test
    public void calcularDigitoModulo11CnabComBaseParaSafra() {

        String digito = CalculoDigitoVerificadorService
                .calcularDigitoModulo11CnabComBase("1200000000015", 7);

        Assert.assertEquals("4", digito);

    }

    @Test
    public void retornarPadraoParaSafra() {

        String padrao = CalculoDigitoVerificadorService
                .retornarPadrao(13, 2, 7, 2, CalculoDigitoVerificadorService.Ordem.DireitaEsquerda);

        Assert.assertEquals("2765432765432", padrao);
    }

    @Test
    public void gerarDigitoMod11Pesos2a9NossoNumeroBancoDoBrasil() {

        String digito = CalculoDigitoVerificadorService
                .gerarDigitoMod11Pesos2a9NossoNumeroSantander("18181700015");

        Assert.assertEquals("4", digito);
    }

    @Test
    public void gerarDigitoMod11Pesos2a9NossoNumeroSantader() {

        String digito = CalculoDigitoVerificadorService
                .gerarDigitoMod11Pesos2a9NossoNumeroSantander("000000000015");

        Assert.assertEquals("9", digito);
    }

}