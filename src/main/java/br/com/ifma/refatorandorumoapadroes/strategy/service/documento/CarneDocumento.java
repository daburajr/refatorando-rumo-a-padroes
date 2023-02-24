package br.com.ifma.refatorandorumoapadroes.strategy.service.documento;

import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto;
import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class CarneDocumento implements Documento {

    private static final TipoBoleto TIPO_DOCUMENTO = TipoBoleto.CARNE;


    @Override
    public boolean executaProcessamento(TipoBoleto tipo) {
        return TIPO_DOCUMENTO.equals(tipo);
    }

    @Override
    public void imprime(List<BoletoItMarket> documentos) {

    }

}
