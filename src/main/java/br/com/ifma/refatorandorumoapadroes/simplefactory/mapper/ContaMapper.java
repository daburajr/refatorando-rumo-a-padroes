package br.com.ifma.refatorandorumoapadroes.simplefactory.mapper;

import br.com.ifma.refatorandorumoapadroes.simplefactory.model.Conta;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface ContaMapper {
    Conta recuperarContaPorId(Long idConta);

    Long recuperarIdBancoPeloIdConta(Long idConta);

    String recuperarCodigoBeneficiarioPelaConta(Long idConta);
}
