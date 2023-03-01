package br.com.ifma.refatorandorumoapadroes.strategy.enumeration;


public enum TipoDocumento {

    BOLETO_LOJA(0, "Boleto Loja"),
    BOLETO_BALCAO(1, "Boleto Balcão"),
    PROMISSORIA(2, "PROMISSORIA"),
    CARNE(3, "CARNÊ");

    private Integer codigo;
    private String descricao;

    private TipoDocumento(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoDocumento toEnum(Integer codigo) throws IllegalAccessException {
        if (codigo == null) {
            return null;
        }

        for (TipoDocumento tipoBoleto : TipoDocumento.values()) {
            if (codigo.equals(tipoBoleto.getCodigo())) {
                return tipoBoleto;
            }
        }

        throw new IllegalAccessException("Codigo de TipoBoleto inválido: " + codigo);

    }
}
