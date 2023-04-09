package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import br.com.ifma.refatorandorumoapadroes.simplefactory.enumeration.SolicitanteNossoNumero;
import br.com.ifma.refatorandorumoapadroes.simplefactory.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.NossoNumeroMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.Conta;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class NossoNumeroService {

    private final NossoNumeroMapper nossoNumeroMapper;

    private final ContaService contaService;

    private final FabricaDeInformacaoNossoNumeroService fabrica;

    private static final short ID_BANCO_DO_BRASIL = 1;
    private static final short ID_BANCO_SANTANDER = 33;
    private static final short ID_BANCO_BRADESCO = 237;
    private static final short ID_BANCO_SAFRA = 422;

    public List<InformacoesNossoNumero> criarNossoNumeroLoja(long filialId,
                                                             int pdv,
                                                             long cupom,
                                                             int quantidade) {

        return Stream.iterate(1, i -> i + 1)
                .limit(quantidade)
                .map(f -> criarInformacoesNossoNumero(filialId, pdv, cupom))
                .collect(Collectors.toList());

    }

    private InformacoesNossoNumero criarInformacoesNossoNumero(long filialId, int pdv, long cupom) {

        Long contaId = this.buscaContaReferenteAFilial(filialId);
        Conta conta = contaService.recuperarContaPorId(contaId);
        long idBanco = contaService.recuperarIdBancoPeloIdConta(contaId);

        String carteiraConta = conta.getCarteira();
        Long nossoNumero = pagaNossoNumero(filialId, pdv, cupom, contaId);

        if (idBanco == ID_BANCO_BRADESCO) {
            return fabrica.criaInformacaoNossoNumeroParaBradesco(nossoNumero, carteiraConta, contaId);
        } else if (idBanco == ID_BANCO_SANTANDER) {
            return fabrica.criaInformacaoNossoNumeroParaSantander(nossoNumero, carteiraConta, contaId);
        } else if (idBanco == ID_BANCO_DO_BRASIL) {
            contaService.validaCodigoDoBeneficiario(contaId);
            return fabrica.criaInformacaoNossoNumeroParaBrasil(nossoNumero, carteiraConta, contaId);
        } else if (idBanco == ID_BANCO_SAFRA) {
            return fabrica.criaInformacaoNossoNumeroParaSafra(nossoNumero, carteiraConta, contaId);
        } else {
            throw new PdvValidationException("Nenhuma Instituiçao Financeira Encontrada.");
        }

    }

    private Long buscaContaReferenteAFilial(Long filialId) {

        Long contaId = nossoNumeroMapper.recuperarIdContaFilial(filialId);

        if (Objects.isNull(contaId) || contaId <= 0) {
            throw new PdvValidationException("Conta inválida.");
        }

        return contaId;
    }

    private Long pagaNossoNumero(Long filialId, Integer pdv, Long cupom, Long idConta) {

        Date data = Calendar.getInstance().getTime();
        int solicitante = SolicitanteNossoNumero.FRENTE_DE_LOJA.getCodigo();

        return nossoNumeroMapper.gerarNossoNumeroProcedure(idConta, solicitante, filialId, pdv, data, cupom);
    }


}