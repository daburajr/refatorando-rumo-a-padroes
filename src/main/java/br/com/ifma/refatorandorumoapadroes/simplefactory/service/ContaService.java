package br.com.ifma.refatorandorumoapadroes.simplefactory.service;


import br.com.ifma.refatorandorumoapadroes.simplefactory.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.ContaMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.Conta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaMapper contaMapper;

    public Conta recuperarContaPorId(Long contaId) {

        Conta conta = contaMapper
                .recuperarContaPorId(contaId);

        if (conta == null) {
            throw new PdvValidationException("Conta inválida.");
        }

        return conta;
    }

    public long recuperarIdBancoPeloIdConta(Long contaId) {

        Long bancoId = contaMapper
                .recuperarIdBancoPeloIdConta(contaId);

        if (bancoId == null || bancoId <= 0) {
            throw new PdvValidationException("Banco inválido..");
        }

        return bancoId;
    }

    public String recuperarCodigoBeneficiarioPelaConta(Long contaId) {

        String codigoBeneficiario = contaMapper
                .recuperarCodigoBeneficiarioPelaConta(contaId);

        if (codigoBeneficiario == null || codigoBeneficiario.trim().isEmpty()) {
            throw new PdvValidationException(
                    "Código de beneficiário não cadastrado para o conta: " + contaId);
        }

        return codigoBeneficiario;
    }

}
