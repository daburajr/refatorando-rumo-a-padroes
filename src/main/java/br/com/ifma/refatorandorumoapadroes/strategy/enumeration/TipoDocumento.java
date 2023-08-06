package br.com.ifma.refatorandorumoapadroes.strategy.enumeration;


import lombok.Getter;

@Getter
public enum TipoDocumento {

    BOLETO_LOJA(0, "Boleto Loja"),
    BOLETO_BALCAO(1, "Boleto Balcão"),
    PROMISSORIA(2, "PROMISSORIA"),
    CARNE(3, "CARNÊ");

    private final Integer codigo;
    private final String descricao;

    TipoDocumento(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static TipoDocumento toEnum(Integer codigo) throws IllegalAccessException {

        if (codigo == null) {
            return null;
        }

        for (TipoDocumento tipoDocumento : TipoDocumento.values()) {
            if (codigo.equals(tipoDocumento.getCodigo())) {
                return tipoDocumento;
            }
        }

        throw new IllegalAccessException("Codigo de TipoBoleto inválido: " + codigo);

    }
}
