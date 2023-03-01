package br.com.ifma.refatorandorumoapadroes.strategy.service;

import br.com.ifma.refatorandorumoapadroes.strategy.enumeration.TipoDocumento;
import br.com.ifma.refatorandorumoapadroes.strategy.mapper.BoletoImpressaoMapper;
import br.com.ifma.refatorandorumoapadroes.strategy.model.DocumentoItMarket;
import br.com.ifma.refatorandorumoapadroes.strategy.service.documento.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImpressaoDocumentoService {

    private final BoletoImpressaoMapper boletoImpressaoMapper;

    private final List<Documento> processaDocumentos;


    public void imprimirDocumentos() {

        List<DocumentoItMarket> documentosPendente = boletoImpressaoMapper.buscarBoletosPedentesDeImpressao();

        Map<TipoDocumento, List<DocumentoItMarket>> boletosAgrupadosPorTipo = documentosPendente.stream()
                .collect(groupingBy(DocumentoItMarket::pegaTipoDocumento));

        boletosAgrupadosPorTipo.forEach((tipo, documentos) -> processaDocumentos.stream()
                .filter(doc -> doc.executaProcessamento(tipo))
                .findAny()
                .ifPresent(doc -> doc.imprime(documentos)));

    }


}