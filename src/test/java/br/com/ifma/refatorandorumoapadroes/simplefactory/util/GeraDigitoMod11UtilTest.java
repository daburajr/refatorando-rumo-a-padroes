package br.com.ifma.refatorandorumoapadroes.simplefactory.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeraDigitoMod11UtilTest {

    @Test
    public void gerarDigitoMod11Pesos2a9NossoNumeroBancoDoBrasil() {

        String digito = GeraDigitoMod11Util
                .geraDigito("18181700015");

        Assert.assertEquals("4", digito);
    }

    @Test
    public void gerarDigitoMod11Pesos2a9NossoNumeroSantader() {

        String digito = GeraDigitoMod11Util
                .geraDigito("000000000015");

        Assert.assertEquals("9", digito);
    }
}