package br.com.ifma.refatorandorumoapadroes.facade.service;

import br.com.ifma.refatorandorumoapadroes.facade.dto.UsuarioDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

public class UsuarioServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UsuarioService usuarioService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveBuscarTodosUsuariosMaiorDeIdade() {

        UsuarioDTO[] usuariosApi = new UsuarioDTO[] {UsuarioDTO.builder().name("teste").age(18).build()};

        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<UsuarioDTO[]>>any())).thenReturn(ResponseEntity.ok(usuariosApi));

        List<UsuarioDTO> result = usuarioService.buscaTodosUsuariosMaiorDeIdade();
        Assert.assertEquals(Collections.singletonList(new UsuarioDTO("teste", 18)), result);
    }

    @Test(expected = RestClientException.class)
    public void naoDeveBuscarTodosUsuariosMaiorDeIdadeParaErroDeComunicacaoComServidor() {

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),
                ArgumentMatchers.<Class<UsuarioDTO[]>>any()))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        usuarioService.buscaTodosUsuariosMaiorDeIdade();

    }

    @Test(expected = RestClientException.class)
    public void naoDeveBuscarTodosUsuariosMaiorDeIdadeParaErroGenerico() {

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),
                ArgumentMatchers.<Class<UsuarioDTO[]>>any()))
                .thenThrow(new RuntimeException("Erro Genérico"));

        usuarioService.buscaTodosUsuariosMaiorDeIdade();

    }

}

