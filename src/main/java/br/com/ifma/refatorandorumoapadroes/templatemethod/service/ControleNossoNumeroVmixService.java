package br.com.ifma.refatorandorumoapadroes.templatemethod.service;


import br.com.ifma.refatorandorumoapadroes.templatemethod.mapper.BoletoNossoNumeroVMixMapper;
import br.com.ifma.refatorandorumoapadroes.templatemethod.model.BoletoNossoNumero;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ControleNossoNumeroVmixService extends TemplateControleService {

    private final BoletoNossoNumeroVMixMapper boletoNossoNumeroVMixMapper;

    @Override
    protected void salvaNossoNumero(BoletoNossoNumero boletoNossoNumero) {
        boletoNossoNumeroVMixMapper
                .inserirBoletoNossoNumeroVMix(boletoNossoNumero);
    }
}
