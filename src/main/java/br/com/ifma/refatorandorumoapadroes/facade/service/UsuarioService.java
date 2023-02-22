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

    private static final String URL = "https://www.redesocialdecidades.org.br/users";

    private final RestTemplate restTemplate;

    public List<UsuarioDTO> buscaTodosUsuariosMaiorDeIdade() {

        try {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

            log.debug("Chamando API de Usuários -->");
            UsuarioDTO[] usuariosApi = restTemplate.exchange(URL, HttpMethod.GET, entity, UsuarioDTO[].class).getBody();

            return this.verificaUsuariosMaioresDeIdade(usuariosApi);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Erro ao chamar API de Usuários. Mensagem: {}. Detalhe: {}.", e.getMessage(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            log.error("Erro ao chamar API de Usuários. Mensagem: {}", e.getMessage());
            throw new RestClientException(e.getMessage());
        }

    }

    private List<UsuarioDTO> verificaUsuariosMaioresDeIdade(UsuarioDTO[] usuariosApi) {
        List<UsuarioDTO> usuarios = Arrays.asList(usuariosApi);
        return usuarios.stream().filter(usuarioDTO -> usuarioDTO.getAge() >= 18).collect(Collectors.toList());
    }

}
