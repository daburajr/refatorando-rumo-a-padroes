package br.com.ifma.refatorandorumoapadroes.strategy.model;

import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoStatusImpressao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoletoItMarket {
    private Long id;
    private Long filialId;
    private Integer pdv;
    private Long cupom;
    private Integer finalizadora;
    private BigDecimal valorBoleto;
    private String cpfOuCnpj;
    private String dataDocumento;
    private String dataVencimento;
    private Long nossoNumero;
    private Long idCliente;
    private Long idImpressora;
    private Integer tipoBoleto;
    private Integer tipoStatusImpressao;
    private Long idPedido;
    private int incidencia;
    private LocalDate dataMovimento;
    private Long cpfProcurador;


    public void adicionaIncidencia() {
        this.setIncidencia(++this.incidencia);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoletoItMarket that = (BoletoItMarket) o;

        if (!filialId.equals(that.filialId)) return false;
        if (!pdv.equals(that.pdv)) return false;
        return cupom.equals(that.cupom);
    }

    @Override
    public int hashCode() {
        int result = filialId.hashCode();
        result = 31 * result + pdv.hashCode();
        result = 31 * result + cupom.hashCode();
        return result;
    }

    public void reimprimir() {
        this.incidencia = 0;
        this.tipoStatusImpressao = TipoStatusImpressao.IMPRESSAO_PEDENTE.getCodigo();
    }
}
