package br.com.ifma.refatorandorumoapadroes.strategy.service.builder;

import br.com.ifma.refatorandorumoapadroes.strategy.model.CupomCapaDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CupomCapaBuilder {

    private CupomCapaBuilder() {}

    public static CupomCapaDTO cupomCapaDTO() {
        return CupomCapaDTO.builder()
                .id(1L)
                .filialId(1L)
                .pdv(700)
                .cupom(3570L)
                .dataMovimento(LocalDateTime.now())
                .dataVenda(LocalDateTime.now())
                .valorTotal(BigDecimal.valueOf(250))
                .enviadoEstoque(true)
                .enviadoFinanceiro(true)
                .pedidoFaturado(789040090L)
                .build();
    }

}
