package br.com.digital.bank.api.steps;

import br.com.digital.bank.api.domain.Conta;
import br.com.digital.bank.api.dto.ContaDto;
import br.com.digital.bank.api.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.cucumber.messages.internal.com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
public class CriarContaStepDefinitions extends StepDefs {

    private Gson gson = new Gson();

    ContaDto contaDto = new ContaDto();

    @Dado("^que seja solicitada a criação de uma nova conta com os seguintes valores$")
    public void que_seja_solicitada_a_criação_de_uma_nova_conta_com_os_seguintes_valores(List<ContaDto> contas) throws IOException {

        contaDto = contas.stream().findFirst().get();

        log.info(contaDto.toString());
    }

    @Quando("^for enviada a solicitação de criação de nova conta$")
    public void for_enviada_a_solicitação_de_criação_de_nova_conta() throws Exception {
        actions = mockMvc.perform(MockMvcRequestBuilders.post("/contas")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contaDto))
        );

        log.info(actions.andReturn().getResponse().getContentAsString());
    }

    @Então("deverá ser apresentada a seguinte mensagem de erro {string}")
    public void deverá_ser_apresentada_a_seguinte_mensagem_de_erro(String mensagemDeErro) throws UnsupportedEncodingException {
        Assert.assertTrue(actions.andReturn().getResponse().getContentAsString().contains(mensagemDeErro));
    }

    @Então("deverá ser apresentada a seguinte mensagem {string}")
    public void deverá_ser_apresentada_a_seguinte_mensagem(String mensagem) throws UnsupportedEncodingException {
        Assert.assertTrue(actions.andReturn().getResponse().getContentAsString().contains(mensagem));

        log.info(actions.andReturn().getResponse().getContentAsString());

        log.info(mensagem);
    }

    @Então("^deverá ser retornado o número da conta criada$")
    public void deverá_ser_retornado_o_número_da_conta_criada() throws Exception {
        String resposta = actions.andReturn().getResponse().getContentAsString();

        ResponseDto responseDto = gson.fromJson(resposta, ResponseDto.class);

        log.info("Conta no formato json utilizando gson: {}", responseDto);

        Conta conta = responseDto.getConta().stream().findFirst().get();

        log.info("Conta extraida do corpo da resposta: {}", conta.toString());

        Assert.assertNotNull(conta.getNumeroConta());
    }
}
