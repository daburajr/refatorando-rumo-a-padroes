package br.com.ifma.refatorandorumoapadroes.simplefactory.enumeration;

public enum SolicitanteNossoNumero {

    FRENTE_DE_LOJA(1), BALCAO(2), INVICTA(3);

    private int codigo;

    private SolicitanteNossoNumero(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}