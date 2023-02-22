package br.com.ifma.refatorandorumoapadroes.simplefactory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InformacoesNossoNumero {

    private Long nossoNumero;

    private String carteira;

    private String digitoVerificadorNossoNumero;

    @JsonIgnore
    private Long idConta;

}
