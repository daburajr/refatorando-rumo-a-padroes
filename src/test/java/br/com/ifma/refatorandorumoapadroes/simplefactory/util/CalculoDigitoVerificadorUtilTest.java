package br.com.ifma.refatorandorumoapadroes.simplefactory.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class CalculoDigitoVerificadorUtilTest {


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void calcularDigitoModulo11CnabComBaseParaBradesco() {

        String digito = CalculoDigitoVerificadorUtil
                .calcularDigitoModulo11CnabComBase("1100000000015", 7);

        Assert.assertEquals("0", digito);

    }

    @Test
    public void retornarPadraoParaBradesco() {

        String padrao = CalculoDigitoVerificadorUtil
                .retornarPadrao(13, 2, 7, 2);

        Assert.assertEquals("2765432765432", padrao);
    }

    @Test
    public void calcularDigitoModulo11CnabComBaseParaSafra() {

        String digito = CalculoDigitoVerificadorUtil
                .calcularDigitoModulo11CnabComBase("1200000000015", 7);

        Assert.assertEquals("4", digito);

    }

    @Test
    public void retornarPadraoParaSafra() {

        String padrao = CalculoDigitoVerificadorUtil
                .retornarPadrao(13, 2, 7, 2);

        Assert.assertEquals("2765432765432", padrao);
    }

    @Test
    public void gerarDigitoMod11Pesos2a9NossoNumeroBancoDoBrasil() {

        String digito = CalculoDigitoVerificadorUtil
                .gerarDigitoMod11Pesos2a9NossoNumero("18181700015");

        Assert.assertEquals("4", digito);
    }

    @Test
    public void gerarDigitoMod11Pesos2a9NossoNumeroSantader() {

        String digito = CalculoDigitoVerificadorUtil
                .gerarDigitoMod11Pesos2a9NossoNumero("000000000015");

        Assert.assertEquals("9", digito);
    }

}