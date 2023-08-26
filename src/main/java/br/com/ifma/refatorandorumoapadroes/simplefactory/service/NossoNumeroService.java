package br.com.ifma.refatorandorumoapadroes.simplefactory.service;

import br.com.ifma.refatorandorumoapadroes.simplefactory.enumeration.SolicitanteNossoNumero;
import br.com.ifma.refatorandorumoapadroes.simplefactory.exception.PdvValidationException;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.ContaMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.mapper.NossoNumeroMapper;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.Conta;
import br.com.ifma.refatorandorumoapadroes.simplefactory.model.InformacoesNossoNumero;
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
public class NossoNumeroService {

    @Autowired
    private NossoNumeroMapper nossoNumeroMapper;

    @Autowired
    private ContaMapper contaMapper;

    private static final long ID_BANCO_DO_BRASIL = 1;
    private static final long ID_BANCO_SANTANDER = 33;
    private static final long ID_BANCO_BRADESCO = 237;
    private static final long ID_BANCO_SAFRA = 422;


    public List<InformacoesNossoNumero> criarNossoNumeroLoja(Long filialId, Integer pdv, Long cupom, Integer qtdeNossoNumero) {
        return criarInformacoesNossoNumeros(filialId, pdv, cupom, qtdeNossoNumero);
    }

    private List<InformacoesNossoNumero> criarInformacoesNossoNumeros(Long filialId, Integer pdv, Long cupom, Integer qtdeNossoNumero) {
        if (qtdeNossoNumero == null)
            throw new PdvValidationException("qtdeNosso número inválido!");

        List<InformacoesNossoNumero> informacoesNossoNumeros = new ArrayList<>();

        for (int quantidade = 0; quantidade < qtdeNossoNumero; quantidade++) {

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

            String digitoVerificadorNossoNumero = null;

            if (idBanco == ID_BANCO_BRADESCO) {
                digitoVerificadorNossoNumero = NossoNumeroFabricaService.calcularDigitoModulo11CnabComBase(
                        carteiraConta + StringUtils.leftPad(Long.toString(nossoNumero), 11, '0'), 7);
            } else if (idBanco == ID_BANCO_SANTANDER) {
                digitoVerificadorNossoNumero = NossoNumeroFabricaService.gerarDigitoMod11Pesos2a9NossoNumeroSantander(StringUtils.leftPad(
                        nossoNumero.toString(), 12, '0'));
            } else if (idBanco == ID_BANCO_DO_BRASIL) {

                String codigoBeneficiario = contaMapper.recuperarCodigoBeneficiarioPelaConta(idConta);

                if (codigoBeneficiario == null || codigoBeneficiario.trim().isEmpty()) {
                    throw new PdvValidationException("Código de beneficiário não cadastrado para o conta: " + idConta);
                }

                nossoNumero = Long.parseLong("181817" + StringUtils.leftPad(nossoNumero.toString(), 5, '0'));

                digitoVerificadorNossoNumero = NossoNumeroFabricaService.gerarDigitoMod11Pesos2a9NossoNumeroSantander(StringUtils.leftPad(nossoNumero
                        .toString().toString(), 11, '0'));
            } else if (idBanco == ID_BANCO_SAFRA) {
                digitoVerificadorNossoNumero = NossoNumeroFabricaService.calcularDigitoModulo11CnabComBase(
                        carteiraConta + StringUtils.leftPad(Long.toString(nossoNumero), 11, '0'), 7);
            }

            InformacoesNossoNumero informacaoNossoNumero = new InformacoesNossoNumero();
            informacaoNossoNumero.setNossoNumero(nossoNumero);
            informacaoNossoNumero.setDigitoVerificadorNossoNumero(digitoVerificadorNossoNumero);
            informacaoNossoNumero.setCarteira(carteiraConta);
            informacaoNossoNumero.setIdConta(idConta);

            informacoesNossoNumeros.add(informacaoNossoNumero);
        }

        return informacoesNossoNumeros;
    }





}