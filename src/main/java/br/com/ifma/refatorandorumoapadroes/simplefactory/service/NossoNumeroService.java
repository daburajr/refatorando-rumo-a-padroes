package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import br.com.ifma.refatorandorumoapadroes.simplefactory.enumeration.SolicitanteNossoNumero;
import br.com.ifma.refatorandorumoapadroes.simplefactory.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.ContaMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.NossoNumeroMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.Conta;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NossoNumeroService {

    private final NossoNumeroMapper nossoNumeroMapper;
    private final ContaMapper contaMapper;
    private final FabricaDeInformacaoNossoNumeroService fabrica;

    private static final long ID_BANCO_DO_BRASIL = 1;
    private static final long ID_BANCO_SANTANDER = 33;
    private static final long ID_BANCO_BRADESCO = 237;
    private static final long ID_BANCO_SAFRA = 422;


    public List<InformacoesNossoNumero> criarNossoNumeroLoja(Long filialId,
                                                             Integer pdv,
                                                             Long cupom,
                                                             Integer qtdeNossoNumero) {

        if (qtdeNossoNumero == null)
            throw new PdvValidationException("qtdeNosso número inválido!");

        List<InformacoesNossoNumero> informacoesNossoNumeros = new ArrayList<>();

        for (int quantidade = 0; quantidade < qtdeNossoNumero; quantidade++) {

            InformacoesNossoNumero informacoesNossoNumero
                    = this.criarInformacoesNossoNumero(filialId, pdv, cupom);

            informacoesNossoNumeros.add(informacoesNossoNumero);
        }

        return informacoesNossoNumeros;
    }

    private InformacoesNossoNumero criarInformacoesNossoNumero(Long filialId, Integer pdv, Long cupom) {

        Long idConta = nossoNumeroMapper.recuperarIdContaFilial(filialId);

        if (idConta == null || idConta <= 0) {
            throw new PdvValidationException("Conta inválida.");
        }

        Conta conta = contaMapper.recuperarContaPorId(idConta);

        if (conta == null) {
            throw new PdvValidationException("Conta inválida.");
        }

        Long idBanco = contaMapper.recuperarIdBancoPeloIdConta(idConta);

        if (idBanco == null || idBanco <= 0) {
            throw new PdvValidationException("Banco inválido..");
        }

        Calendar data = Calendar.getInstance();
        String carteiraConta = null;
        carteiraConta = conta.getCarteira();
        carteiraConta = carteiraConta == null ? "" : carteiraConta.trim();

        Long nossoNumero = nossoNumeroMapper.gerarNossoNumeroProcedure(idConta, SolicitanteNossoNumero.FRENTE_DE_LOJA.getCodigo(),
                filialId, pdv, data.getTime(), cupom);

        if (idBanco == ID_BANCO_BRADESCO) {
            return fabrica.criaInformacaoNossoNumeroParaBradesco(nossoNumero, carteiraConta, idConta);
        } else if (idBanco == ID_BANCO_SANTANDER) {
            return fabrica.criaInformacaoNossoNumeroParaSantander(nossoNumero, carteiraConta, idConta);
        } else if (idBanco == ID_BANCO_DO_BRASIL) {

            String codigoBeneficiario = contaMapper.recuperarCodigoBeneficiarioPelaConta(idConta);

            if (codigoBeneficiario == null || codigoBeneficiario.trim().isEmpty()) {
                throw new PdvValidationException("Código de beneficiário não cadastrado para o conta: " + idConta);
            }

            return fabrica.criaInformacaoNossoNumeroParaBrasil(nossoNumero, carteiraConta, idConta);

        } else if (idBanco == ID_BANCO_SAFRA) {
            return fabrica.criaInformacaoNossoNumeroParaSafra(nossoNumero, carteiraConta, idConta);
        } else {
            throw new PdvValidationException("Banco nao encontrado.");
        }

    }




}