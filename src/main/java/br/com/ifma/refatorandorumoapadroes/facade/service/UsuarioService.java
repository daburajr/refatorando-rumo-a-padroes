package br.com.ifma.refatorandorumoapadroes.facade.service;

import br.com.ifma.refatorandorumoapadroes.facade.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioClient usuarioClient;
    private static final int MAIOR_DE_IDADE = 18;

    public List<UsuarioDTO> buscaTodosUsuariosMaiorDeIdade() {
        UsuarioDTO[] usuarioDTOS = usuarioClient.buscaTodosUsuarios();
        return this.verificaUsuariosMaioresDeIdade(usuarioDTOS);
    }

    private List<UsuarioDTO> verificaUsuariosMaioresDeIdade(UsuarioDTO[] usuariosApi) {
        List<UsuarioDTO> usuarios = Arrays.asList(usuariosApi);
        return usuarios.stream()
                .filter(usuarioDTO -> usuarioDTO.getAge() >= MAIOR_DE_IDADE)
                .collect(Collectors.toList());
    }

}
