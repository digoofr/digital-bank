package br.com.digital.bank.api.steps;

import br.com.digital.bank.api.dto.TransferenciaDto;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
public class TransferenciaStepDefinitions extends StepDefs {

    TransferenciaDto transferenciaDto = new TransferenciaDto();

    @Dado("que seja solicitada um transferência com as seguintes informações")
    public void que_seja_solicitada_um_transferência_com_as_seguintes_informações(List<TransferenciaDto> listaDeTransferencia) {
        transferenciaDto = listaDeTransferencia.stream().findFirst().get();
        log.info(transferenciaDto.toString());
    }

    @Quando("for executada a operação de transferência")
    public void for_executada_a_operação_de_transferência() throws Exception {
        actions = mockMvc.perform(put("/contas/transferencias")
                .content(objectMapper.writeValueAsString(transferenciaDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

}
