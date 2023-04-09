package br.com.ifma.refatorandorumoapadroes.simplefactory.service;


import br.com.ifma.refatorandorumoapadroes.simplefactory.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.ContaMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.Conta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaMapper contaMapper;


    public Conta recuperarContaPorId(long contaId) {
        return Optional.of(contaMapper.recuperarContaPorId(contaId))
                .orElseThrow(() -> new PdvValidationException("Conta inválida."));
    }

    public long recuperarIdBancoPeloIdConta(long contaId) {

        long bancoId = Optional.of(contaMapper.recuperarIdBancoPeloIdConta(contaId))
                .orElseThrow(() -> new PdvValidationException("Banco nao encontrado."));

        if (bancoId <= 0) {
            throw new PdvValidationException("Conta com banco invalido.");
        }

        return bancoId;
    }

    public void validaCodigoDoBeneficiario(long contaId) {

        String mensagemDeErro = "Código de beneficiário não cadastrado para o conta: " + contaId;

        String codigoBeneficiario = Optional.of(contaMapper.recuperarCodigoBeneficiarioPelaConta(contaId))
                .orElseThrow(() -> new PdvValidationException(mensagemDeErro));

        if (codigoBeneficiario.trim().isEmpty()) {
            throw new PdvValidationException(mensagemDeErro);
        }

    }


}
