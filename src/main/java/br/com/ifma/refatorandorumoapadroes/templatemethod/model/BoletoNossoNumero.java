package br.com.ifma.refatorandorumoapadroes.templatemethod.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BoletoNossoNumero {

    private long id;

    private long idFilial;

    private int pdv;

    private long nossoNumero;

    private Date data;

    private int status;

}
