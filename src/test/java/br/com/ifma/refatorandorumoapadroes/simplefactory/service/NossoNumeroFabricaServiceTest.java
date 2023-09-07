package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;
import br.com.ifma.refatorandorumoapadroes.simplefactory.service.builder.InformacaoNossoNumeroBuilder;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NossoNumeroFabricaServiceTest {

    @Test
    public void criaInformacaoParaBradesco() {

        InformacoesNossoNumero informacoesNossoNumero = NossoNumeroFabricaService
                .criaInformacaoParaBradesco(15L, "11", 237);

        assertEquals(informacoesNossoNumero, InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroBradesco());

    }

    @Test
    public void criaInformacaoParaSantander() {

        InformacoesNossoNumero informacoesNossoNumero = NossoNumeroFabricaService
                .criaInformacaoParaSantander(15L, "11", 33);

        assertEquals(informacoesNossoNumero, InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroSantander());

    }

    @Test
    public void criaInformacaoParaBancoDoBrasil() {
        InformacoesNossoNumero informacoesNossoNumero = NossoNumeroFabricaService
                .criaInformacaoParaBancoDoBrasil(15L, "10", 1);

        assertEquals(informacoesNossoNumero, InformacaoNossoNumeroBuilder.criaInformacaoNossoNumeroBB());
    }

    @Test
    public void criaInformacaoParaSafra() {

        InformacoesNossoNumero informacoesNossoNumero = NossoNumeroFabricaService
                .criaInformacaoParaSafra(15L, "12", 422);

        assertEquals(informacoesNossoNumero, InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroSafra());

    }
}