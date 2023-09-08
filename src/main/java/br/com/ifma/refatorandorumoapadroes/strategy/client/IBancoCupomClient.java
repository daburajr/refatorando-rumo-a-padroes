package br.com.ifma.refatorandorumoapadroes.strategy.client;

import br.com.ifma.refatorandorumoapadroes.strategy.model.CupomCapaDTO;
import org.springframework.stereotype.Service;

@Service
public interface IBancoCupomClient {
    CupomCapaDTO buscarCupomCapa(Long filialId, Integer pdv, Long cupom);
}
