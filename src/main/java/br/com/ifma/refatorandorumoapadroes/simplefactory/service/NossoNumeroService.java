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
                String digitoVerificadorNossoNumero = pegaDigitoVerificadorNossoNumeroComCarteira(carteiraConta, nossoNumero);

                informacaoNossoNumero = new InformacoesNossoNumero();
                informacaoNossoNumero.setNossoNumero(nossoNumero);
                informacaoNossoNumero.setDigitoVerificadorNossoNumero(digitoVerificadorNossoNumero);
                informacaoNossoNumero.setCarteira(carteiraConta);
                informacaoNossoNumero.setIdConta(idConta);

            } else if (idBanco == ID_BANCO_SANTANDER) {
                String digitoVerificadorNossoNumero = pegaDigitoVerificadorNossoNumero(nossoNumero, 12);

                informacaoNossoNumero = new InformacoesNossoNumero();
                informacaoNossoNumero.setNossoNumero(nossoNumero);
                informacaoNossoNumero.setDigitoVerificadorNossoNumero(digitoVerificadorNossoNumero);
                informacaoNossoNumero.setCarteira(carteiraConta);
                informacaoNossoNumero.setIdConta(idConta);

            } else if (idBanco == ID_BANCO_DO_BRASIL) {
                contaService.recuperarCodigoBeneficiarioPelaConta(idConta);
                nossoNumero = Long.parseLong("181817" + StringUtils.leftPad(nossoNumero.toString(), 5, '0'));
                String digitoVerificadorNossoNumero = pegaDigitoVerificadorNossoNumero(nossoNumero, 11);

                informacaoNossoNumero = new InformacoesNossoNumero();
                informacaoNossoNumero.setNossoNumero(nossoNumero);
                informacaoNossoNumero.setDigitoVerificadorNossoNumero(digitoVerificadorNossoNumero);
                informacaoNossoNumero.setCarteira(carteiraConta);
                informacaoNossoNumero.setIdConta(idConta);

            } else if (idBanco == ID_BANCO_SAFRA) {
                String digitoVerificadorNossoNumero = pegaDigitoVerificadorNossoNumeroComCarteira(carteiraConta, nossoNumero);

                informacaoNossoNumero = new InformacoesNossoNumero();
                informacaoNossoNumero.setNossoNumero(nossoNumero);
                informacaoNossoNumero.setDigitoVerificadorNossoNumero(digitoVerificadorNossoNumero);
                informacaoNossoNumero.setCarteira(carteiraConta);
                informacaoNossoNumero.setIdConta(idConta);
            }



            informacoesNossoNumeros.add(informacaoNossoNumero);
        }

        return informacoesNossoNumeros;
    }

    private static String pegaDigitoVerificadorNossoNumeroComCarteira(String carteiraConta, Long nossoNumero) {
        return CalculoDigitoVerificadorService.calcularDigitoModulo11CnabComBase(
                carteiraConta + StringUtils.leftPad(Long.toString(nossoNumero), 11, '0'), 7);
    }

    private static String pegaDigitoVerificadorNossoNumero(Long nossoNumero, int tamanho) {
        return CalculoDigitoVerificadorService.gerarDigitoMod11Pesos2a9NossoNumeroSantander(StringUtils.leftPad(
                nossoNumero.toString(), tamanho, '0'));
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