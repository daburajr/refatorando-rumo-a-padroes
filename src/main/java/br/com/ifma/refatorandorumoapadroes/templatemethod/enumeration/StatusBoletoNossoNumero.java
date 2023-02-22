package br.com.ifma.refatorandorumoapadroes.templatemethod.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum StatusBoletoNossoNumero {

    PRE_INSERIDO(0), INSERIDO(1), PROCESSADO(2);

    static Map<Integer, StatusBoletoNossoNumero> map = null;
    public Integer idStatus;

    StatusBoletoNossoNumero(Integer idStatus) {
        this.idStatus = idStatus;
    }

    static void inicializar() {
        if (map == null) {
            map = new HashMap<>();
            map.put(PRE_INSERIDO.idStatus, PRE_INSERIDO);
            map.put(INSERIDO.idStatus, INSERIDO);
            map.put(PROCESSADO.idStatus, PROCESSADO);
        }
    }

    public static StatusBoletoNossoNumero parse(Integer idStatus) {
        inicializar();
        return map.get(idStatus);
    }

}