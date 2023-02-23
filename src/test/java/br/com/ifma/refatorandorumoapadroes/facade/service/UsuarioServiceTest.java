package br.com.ifma.refatorandorumoapadroes.facade.service;

import br.com.ifma.refatorandorumoapadroes.facade.dto.UsuarioDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

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

        UsuarioDTO[] usuarios = new UsuarioDTO[]{
                UsuarioDTO.builder().name("Bruno").age(18).build(),
                UsuarioDTO.builder().name("Pedro").age(10).build()
        };

        List<UsuarioDTO> expected = Collections
                .singletonList(UsuarioDTO.builder().name("Bruno").age(18).build());

        when(usuarioClient.buscaTodosUsuarios()).thenReturn(usuarios);

        List<UsuarioDTO> result = usuarioService.buscaTodosUsuariosMaiorDeIdade();

        assertEquals(expected, result);

    }
}

