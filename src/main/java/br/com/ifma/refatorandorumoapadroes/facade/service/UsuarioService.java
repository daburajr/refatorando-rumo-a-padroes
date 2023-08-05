package br.com.ifma.refatorandorumoapadroes.facade.service;

import br.com.ifma.refatorandorumoapadroes.facade.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {



    public List<UsuarioDTO> buscaTodosUsuariosMaiorDeIdade() {
        UsuarioDTO[] usuarioDTOS = buscaTodosUsuarios();
        return this.verificaUsuariosMaioresDeIdade(usuarioDTOS);
    }

    private List<UsuarioDTO> verificaUsuariosMaioresDeIdade(UsuarioDTO[] usuariosApi) {
        List<UsuarioDTO> usuarios = Arrays.asList(usuariosApi);
        return usuarios.stream().filter(usuarioDTO -> usuarioDTO.getAge() >= 18).collect(Collectors.toList());
    }

}
