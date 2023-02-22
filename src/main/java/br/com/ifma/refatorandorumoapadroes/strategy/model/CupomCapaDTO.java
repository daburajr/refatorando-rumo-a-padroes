package br.com.ifma.refatorandorumoapadroes.strategy.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CupomCapaDTO {

    private Long id;

    private Long filialId;

    private Integer pdv;

    private Long cupom;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataMovimento;

    private BigDecimal valorTotal;

    private Boolean enviadoEstoque;

    private Boolean enviadoFinanceiro;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataVenda;

    private Boolean cancelado;

    private Long pedidoFaturado;

}