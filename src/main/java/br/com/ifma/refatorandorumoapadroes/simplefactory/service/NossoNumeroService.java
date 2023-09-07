package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import br.com.ifma.refatorandorumoapadroes.simplefactory.enumeration.SolicitanteNossoNumero;
import br.com.ifma.refatorandorumoapadroes.simplefactory.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.NossoNumeroMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.Conta;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NossoNumeroService {

    private final NossoNumeroMapper nossoNumeroMapper;

    private final ContaService contaService;

    private static final long ID_BANCO_DO_BRASIL = 1;
    private static final long ID_BANCO_SANTANDER = 33;
    private static final long ID_BANCO_BRADESCO = 237;
    private static final long ID_BANCO_SAFRA = 422;


    public List<InformacoesNossoNumero> criarNossoNumeroLoja(Long filialId,
                                                             Integer pdv,
                                                             Long cupom,
                                                             Integer qtdeNossoNumero) {
        return criarInformacoesNossoNumeros(filialId, pdv, cupom, qtdeNossoNumero);
    }

    private List<InformacoesNossoNumero> criarInformacoesNossoNumeros(Long filialId,
                                                                      Integer pdv,
                                                                      Long cupom,
                                                                      Integer qtdeNossoNumero) {
        if (qtdeNossoNumero == null)
            throw new PdvValidationException("qtdeNosso número inválido!");

        List<InformacoesNossoNumero> informacoesNossoNumeros = new ArrayList<>();

        for (int quantidade = 0; quantidade < qtdeNossoNumero; quantidade++) {

            Long idConta = this.recuperaContaDaFilial(filialId);
            Conta conta = contaService.recuperarContaPorId(idConta);
            long idBanco = contaService.recuperarIdBancoPeloIdConta(idConta);

            String carteiraConta = conta.getCarteira();
            Long nossoNumero = this.geraNossoNumeroPara(filialId, pdv, cupom);

            InformacoesNossoNumero informacaoNossoNumero = null;

            if (idBanco == ID_BANCO_BRADESCO) {
                informacaoNossoNumero = NossoNumeroFabricaService.criaInformacaoParaBradesco(nossoNumero, carteiraConta, idConta);
            } else if (idBanco == ID_BANCO_SANTANDER) {
                informacaoNossoNumero = NossoNumeroFabricaService.criaInformacaoParaSantander(nossoNumero, carteiraConta, idConta);
            } else if (idBanco == ID_BANCO_DO_BRASIL) {
                contaService.recuperarCodigoBeneficiarioPelaConta(idConta);
                informacaoNossoNumero = NossoNumeroFabricaService.criaInformacaoParaBancoDoBrasil(nossoNumero, carteiraConta, idConta);
            } else if (idBanco == ID_BANCO_SAFRA) {
                informacaoNossoNumero = NossoNumeroFabricaService.criaInformacaoParaSafra(nossoNumero, carteiraConta, idConta);
            }

            informacoesNossoNumeros.add(informacaoNossoNumero);
        }

        return informacoesNossoNumeros;
    }



    public Long recuperaContaDaFilial(Long filialId) {
        return  Optional.ofNullable(nossoNumeroMapper.recuperarIdContaFilial(filialId))
                .orElseThrow(() -> new PdvValidationException("Conta inválida."));
    }

    private Long geraNossoNumeroPara(Long filialId, Integer pdv, Long cupom) {

        Long idConta = this.recuperaContaDaFilial(filialId);
        Date data = Calendar.getInstance().getTime();
        int solicitante = SolicitanteNossoNumero.FRENTE_DE_LOJA.getCodigo();

        return nossoNumeroMapper.gerarNossoNumeroProcedure(idConta, solicitante, filialId, pdv, data, cupom);

    }




}