package br.com.ifma.refatorandorumoapadroes.templatemethod.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BoletoNossoNumeroVMix {
    private Long id;
    private Long idFilial;
    private Integer pdv;
    private Long nossoNumero;
    private Date data;
    private Integer status;
}
