package br.com.ifma.refatorandorumoapadroes.strategy.service;

import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoBoleto;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.BoletoItMarket;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;


@Slf4j
@Service
@RequiredArgsConstructor
public class ImpressaoBoletoService {

    private final BoletoImpressaoMapper boletoImpressaoMapper;
    private final List<Documento> processaDocumentos;

    public void imprimirBoletos() {

        List<BoletoItMarket> documentosPendente = boletoImpressaoMapper
                .buscarBoletosPedentesDeImpressao();

        Map<TipoBoleto, List<BoletoItMarket>> boletosAgrupadosPorTipo = documentosPendente.stream()
                .collect(groupingBy(BoletoItMarket::pegaTipoDocumento));

        boletosAgrupadosPorTipo.forEach((tipo, documentos) -> processaDocumentos.stream()
                .filter(doc -> doc.executaProcessamento(tipo))
                .findAny()
                .ifPresent(doc -> doc.imprime(documentos)));


    }


}