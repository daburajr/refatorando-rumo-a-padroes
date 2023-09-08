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

    private final NossoNumeroFabricaService nossoNumeroFabricaService;

    private static final long ID_BANCO_DO_BRASIL = 1;
    private static final long ID_BANCO_SANTANDER = 33;
    private static final long ID_BANCO_BRADESCO = 237;
    private static final long ID_BANCO_SAFRA = 422;


    public List<InformacoesNossoNumero> criarNossoNumeroLoja(Long filialId,
                                                             Integer pdv,
                                                             Long cupom,
                                                             Integer qtdeNossoNumero) {

        if (Objects.isNull(qtdeNossoNumero)) {
            throw new PdvValidationException("qtdeNosso número inválido!");
        }

        return Stream.iterate(1, i -> i + 1)
                .limit(qtdeNossoNumero)
                .map(f -> criarInformacoesNossoNumero(filialId, pdv, cupom))
                .collect(Collectors.toList());
    }

    public Long recuperaContaDaFilial(Long filialId) {
        return  Optional.ofNullable(nossoNumeroMapper.recuperarIdContaFilial(filialId))
                .orElseThrow(() -> new PdvValidationException("Conta inválida."));
    }

    private InformacoesNossoNumero criarInformacoesNossoNumero(long filialId, int pdv, long cupom) {

        Long idConta = this.recuperaContaDaFilial(filialId);
        Conta conta = contaService.recuperarContaPorId(idConta);
        long idBanco = contaService.recuperarIdBancoPeloIdConta(idConta);

        String carteiraConta = conta.getCarteira();
        Long nossoNumero = this.geraNossoNumeroPara(filialId, pdv, cupom);

        if (idBanco == ID_BANCO_BRADESCO) {
            return nossoNumeroFabricaService.criaInformacaoParaSafraOuBradesco(nossoNumero, carteiraConta, idConta);
        } else if (idBanco == ID_BANCO_SANTANDER) {
            return nossoNumeroFabricaService.criaInformacaoParaSantander(nossoNumero, carteiraConta, idConta);
        } else if (idBanco == ID_BANCO_DO_BRASIL) {
            contaService.recuperarCodigoBeneficiarioPelaConta(idConta);
            return nossoNumeroFabricaService.criaInformacaoParaBancoDoBrasil(nossoNumero, carteiraConta, idConta);
        } else if (idBanco == ID_BANCO_SAFRA) {
            return nossoNumeroFabricaService.criaInformacaoParaSafraOuBradesco(nossoNumero, carteiraConta, idConta);
        } else {
            throw new PdvValidationException("Nenhuma Instituiçao Financeira Encontrada.");
        }

    }

    private Long geraNossoNumeroPara(Long filialId, Integer pdv, Long cupom) {

        Long idConta = this.recuperaContaDaFilial(filialId);
        Date data = Calendar.getInstance().getTime();
        int solicitante = SolicitanteNossoNumero.FRENTE_DE_LOJA.getCodigo();

        return nossoNumeroMapper.gerarNossoNumeroProcedure(idConta, solicitante, filialId, pdv, data, cupom);

    }

}