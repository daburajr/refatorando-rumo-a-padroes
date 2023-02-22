package br.com.ifma.refatorandorumoapadroes.strategy.enumeration;

public enum TipoStatusImpressao {

    IMPRESSAO_COM_ERRO(2, "Impressão com Erro"),
    IMPRESSAO_CONCLUIDA(1, "Impressão Concluida"),
    IMPRESSAO_PEDENTE(0, "Impressão Pendente");

    private Integer codigo;
    private String descricao;

    private TipoStatusImpressao(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoStatusImpressao toEnum(Integer codigo) throws IllegalAccessException {
        if (codigo == null) {
            return null;
        }

        for (TipoStatusImpressao tipoStatusImpressao : TipoStatusImpressao.values()) {
            if (codigo.equals(tipoStatusImpressao.getCodigo())) {
                return tipoStatusImpressao;
            }
        }

        throw new IllegalAccessException("Codigo de TipoStatusImpressao inválido: " + codigo);

    }

}