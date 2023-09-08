package br.com.ifma.refatorandorumoapadroes.simplefactory.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface NossoNumeroMapper {
    Long recuperarIdContaFilial(Long filialId);

    Long gerarNossoNumeroProcedure(Long idConta, int codigo, Long filialId, Integer pdv, Date time, Long cupom);
}
