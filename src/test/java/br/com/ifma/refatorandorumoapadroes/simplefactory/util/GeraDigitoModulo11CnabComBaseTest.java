package br.com.ifma.refatorandorumoapadroes.simplefactory.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class GeraDigitoModulo11CnabComBaseTest {


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void calcularDigitoModulo11CnabComBaseParaBradesco() {

        String digito = GeraDigitoModulo11CnabComBase
                .calcularDigitoModulo11CnabComBase("1100000000015", 7);

        Assert.assertEquals("0", digito);

    }

    @Test
    public void retornarPadraoParaBradesco() {

        String padrao = GeraDigitoModulo11CnabComBase
                .retornarPadrao(13, 2, 7, 2);

        Assert.assertEquals("2765432765432", padrao);
    }

    @Test
    public void calcularDigitoModulo11CnabComBaseParaSafra() {

        String digito = GeraDigitoModulo11CnabComBase
                .calcularDigitoModulo11CnabComBase("1200000000015", 7);

        Assert.assertEquals("4", digito);

    }

    @Test
    public void retornarPadraoParaSafra() {

        String padrao = GeraDigitoModulo11CnabComBase
                .retornarPadrao(13, 2, 7, 2);

        Assert.assertEquals("2765432765432", padrao);
    }

}