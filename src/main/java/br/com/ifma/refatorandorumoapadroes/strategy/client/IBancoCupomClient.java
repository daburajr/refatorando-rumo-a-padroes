package br.com.ifma.refatorandorumoapadroes.strategy.client;

import br.com.ifma.refatorandorumoapadroes.strategy.model.CupomCapaDTO;

public interface IBancoCupomClient {
    CupomCapaDTO buscarCupomCapa(Long filialId, Integer pdv, Long cupom);
}
