package br.com.ifma.refatorandorumoapadroes.simplefactory.mapper;

import java.util.Date;

public interface NossoNumeroMapper {
    Long recuperarIdContaFilial(Long filialId);

    Long gerarNossoNumeroProcedure(Long idConta, int codigo, Long filialId, Integer pdv, Date time, Long cupom);
}
