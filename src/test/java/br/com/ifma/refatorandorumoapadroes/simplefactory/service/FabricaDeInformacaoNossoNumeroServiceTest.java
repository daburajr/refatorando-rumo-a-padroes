package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;
import br.com.ifma.refatorandorumoapadroes.simplefactory.service.builder.InformacaoNossoNumeroBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class FabricaDeInformacaoNossoNumeroServiceTest {

    @InjectMocks
    private FabricaDeInformacaoNossoNumeroService fabricaDeInformacaoNossoNumeroService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveCriaInformacaoNossoNumeroParaBradesco() {

        InformacoesNossoNumero result = fabricaDeInformacaoNossoNumeroService
                .criaInformacaoNossoNumeroParaBradesco(15L, "11", 237L);

        Assert.assertEquals(InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroBradesco(), result);
    }

    @Test
    public void deveCriaInformacaoNossoNumeroParaSantander() {

        InformacoesNossoNumero result = fabricaDeInformacaoNossoNumeroService
                .criaInformacaoNossoNumeroParaSantander(15L, "11", 33L);

        Assert.assertEquals(InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroSantander(), result);
    }

    @Test
    public void deveCriarInformacaoNossoNumeroParaBrasil() {

        InformacoesNossoNumero result = fabricaDeInformacaoNossoNumeroService
                .criaInformacaoNossoNumeroParaBrasil(15L, "10", 1L);

        Assert.assertEquals(InformacaoNossoNumeroBuilder.criaInformacaoNossoNumeroBB(), result);
    }

    @Test
    public void deveCriarInformacaoNossoNumeroParaSafra() {

        InformacoesNossoNumero result = fabricaDeInformacaoNossoNumeroService
                .criaInformacaoNossoNumeroParaSafra(15L, "12", 422L);

        Assert.assertEquals(InformacaoNossoNumeroBuilder.criaInformacoesNossoNumeroSafra(), result);
    }
}

