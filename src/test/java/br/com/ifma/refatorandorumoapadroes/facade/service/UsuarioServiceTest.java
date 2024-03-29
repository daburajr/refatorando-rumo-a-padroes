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
    private UsuarioClient usuarioClient;

    @InjectMocks
    private UsuarioService usuarioService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveBuscarTodosUsuariosMaiorDeIdade() {

        UsuarioDTO[] usuariosApi = new UsuarioDTO[] {
                UsuarioDTO.builder()
                        .name("test01")
                        .age(18)
                        .build(),
                UsuarioDTO.builder()
                        .name("test02")
                        .age(10)
                        .build(),
        };

        when(usuarioClient.buscaTodosUsuarios()).thenReturn(usuariosApi);

        List<UsuarioDTO> result = usuarioService.buscaTodosUsuariosMaiorDeIdade();
        Assert.assertEquals(List.of(new UsuarioDTO("test01", 18)), result);
    }


}

