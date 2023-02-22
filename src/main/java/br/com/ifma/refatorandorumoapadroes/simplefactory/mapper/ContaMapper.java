package br.com.ifma.refatorandorumoapadroes.simplefactory.mapper;

import br.com.ifma.refatorandorumoapadroes.simplefactory.model.Conta;

public interface ContaMapper {
    Conta recuperarContaPorId(Long idConta);

    Long recuperarIdBancoPeloIdConta(Long idConta);

    String recuperarCodigoBeneficiarioPelaConta(Long idConta);
}
