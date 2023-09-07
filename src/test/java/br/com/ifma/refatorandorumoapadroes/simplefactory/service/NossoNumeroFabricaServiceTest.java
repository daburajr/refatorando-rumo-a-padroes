package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;
import br.com.ifma.refatorandorumoapadroes.simplefactory.service.builder.InformacaoNossoNumeroBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class NossoNumeroFabricaServiceTest {

    @InjectMocks
    private NossoNumeroFabricaService nossoNumeroFabricaService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void criaInformacaoParaBradesco() {

        InformacoesNossoNumero informacoesNossoNumero = nossoNumeroFabricaService
                .criaInformacaoParaBradesco(15L, "11", 237);

        assertEquals(informacoesNossoNumero, InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroBradesco());

    }

    @Test
    public void criaInformacaoParaSantander() {

        InformacoesNossoNumero informacoesNossoNumero = nossoNumeroFabricaService
                .criaInformacaoParaSantander(15L, "11", 33);

        assertEquals(informacoesNossoNumero, InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroSantander());

    }

    @Test
    public void criaInformacaoParaBancoDoBrasil() {
        InformacoesNossoNumero informacoesNossoNumero = nossoNumeroFabricaService
                .criaInformacaoParaBancoDoBrasil(15L, "10", 1);

        assertEquals(informacoesNossoNumero, InformacaoNossoNumeroBuilder.criaInformacaoNossoNumeroBB());
    }

    @Test
    public void criaInformacaoParaSafra() {

        InformacoesNossoNumero informacoesNossoNumero = nossoNumeroFabricaService
                .criaInformacaoParaSafra(15L, "12", 422);

        assertEquals(informacoesNossoNumero, InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroSafra());

    }
}