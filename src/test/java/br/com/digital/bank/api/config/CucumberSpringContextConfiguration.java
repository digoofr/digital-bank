package br.com.digital.bank.api.config;

import br.com.digital.bank.api.DigitalBankApplication;
import br.com.digital.bank.api.domain.Conta;
import br.com.digital.bank.api.domain.Pessoa;
import br.com.digital.bank.api.domain.PessoaFisica;
import br.com.digital.bank.api.dto.ContaDto;
import br.com.digital.bank.api.dto.TransferenciaDto;
import br.com.digital.bank.api.repository.ContaRepository;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@CucumberContextConfiguration
@ActiveProfiles("dev")
@ContextConfiguration(classes = DigitalBankApplication.class, loader = SpringBootContextLoader.class)
@Slf4j
public class CucumberSpringContextConfiguration {

    @Autowired
    ContaRepository contaRepository;

    @DataTableType
    public ContaDto contaDtoDataTable(Map<String,String> map) {
        return new ContaDto(
            map.get("Nome"),
            map.get("Cpf"),
            Double.parseDouble(map.get("Saldo"))
        );
    }
    @DataTableType
    public Conta contaDataTable(Map<String, String> map) {
        Pessoa pessoa = new Pessoa();
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoa.setNome("teste");
        pessoaFisica.setCpf("0532266644");
            return  new Conta(pessoa,Double.parseDouble(map.get("Saldo")),Long.parseLong(map.get("Numero Conta")));
    }
    @DataTableType
    public TransferenciaDto tranferenciaDataTable(Map<String,String> map) {
        return new TransferenciaDto(
          Long.parseLong(map.get("Conta do Solicitante")),
          Double.parseDouble(map.get("Valor")),
          Long.parseLong(map.get("Conta do Beneficiário"))
        );
    }

    @Before
    public void setUp() {
        log.info("Limpando Base de Dados");
        contaRepository.deleteAll();
        log.info("-------------- Testes Inicializados --------------");
    }
}
