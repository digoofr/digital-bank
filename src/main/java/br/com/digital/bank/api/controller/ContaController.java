package br.com.digital.bank.api.controller;

import br.com.digital.bank.api.domain.Conta;
import br.com.digital.bank.api.domain.Pessoa;
import br.com.digital.bank.api.domain.PessoaFisica;
import br.com.digital.bank.api.dto.ContaDto;
import br.com.digital.bank.api.dto.ResponseDto;
import br.com.digital.bank.api.dto.TransferenciaDto;
import br.com.digital.bank.api.service.ContaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.digital.bank.api.utils.VariaveisUtils.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contas")
@CrossOrigin(origins = "*")
@Slf4j
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<ResponseDto> criar(@Valid @RequestBody ContaDto contaDto) {
        log.info("Ordem de nova conta: {}", contaDto);

        Conta conta = transf(contaDto);

        conta.setNumeroConta(contaService.gerarNumeroDaConta());
        contaService.verificarLimiteAoCriarConta(conta);
        contaService.verificarCpfVazio(contaDto.getPessoaFisica().getCpf());
        contaService.verificarCpfInvalido(contaDto.getPessoaFisica().getCpf());
        contaService.create(conta);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(MSG_CONTA_CADASTRADA, conta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deletar(@PathVariable Long id) {
        contaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDto(MSG_CONTA_EXCLUIDA));
    }

    @GetMapping
    public ResponseEntity<List<Conta>> listar() {
        log.info("Listar todas as Contas");
        return ResponseEntity.ok().body(contaService.findAll());
    }

    @GetMapping("/{numeroDaConta}")
    public ResponseEntity<Conta> buscarPorNumeroDaConta(@PathVariable Long numeroDaConta) {
        log.info("Buscar conta por numero da conta: {}", numeroDaConta);
        Conta conta = contaService.findByNumeroConta(numeroDaConta);
        return ResponseEntity.ok().body(conta);
    }

    @PutMapping("/depositos/{numeroDaConta}/{valor}")
    public ResponseEntity<ResponseDto> depositar(@PathVariable Long numeroDaConta, @PathVariable Double valor) {
        Conta conta = contaService.findByNumeroConta(numeroDaConta);
        contaService.depositar(conta, valor);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ResponseDto(MSG_DEPOSITO_REALIZADO));
    }

    @PutMapping("/saques/{numeroDaConta}/{valor}")
    public ResponseEntity<ResponseDto> sacar(@PathVariable Long numeroDaConta, @PathVariable Double valor) {
        Conta conta = contaService.findByNumeroConta(numeroDaConta);
        contaService.verificarLimiteMinimo(valor);
        contaService.vereficarSaldoNaConta(conta, valor);
        contaService.sacar(conta, valor);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ResponseDto(MSG_SAQUE_REALIZADO));
    }

    @PutMapping("/transferencias")
    public ResponseEntity<ResponseDto> tranferir(@Valid @RequestBody TransferenciaDto transferenciaDto) {
        Conta contaSolicitante = contaService.findByNumeroConta(transferenciaDto.getContaDoSolicitante());
        Conta contaBeneficiario = contaService.findByNumeroConta(transferenciaDto.getContaDoBeneficiario());
        contaService.vereficarSaldoNaConta(contaSolicitante, transferenciaDto.getValor());
        contaService.verificarLimiteMinimo(transferenciaDto.getValor());
        contaService.transferir(contaSolicitante, contaBeneficiario, transferenciaDto.getValor());
        return ResponseEntity.ok().body(new ResponseDto(MSG_TRANSFERENCIA_REALIZADA));
    }

    private Conta transf(ContaDto contaDto) {
        Conta conta = new Conta();
        Pessoa pessoa = new Pessoa();
        PessoaFisica pessoaFisica = new PessoaFisica();
        conta.setSaldo(contaDto.getSaldo());
        pessoa.setNome(contaDto.getPessoa().getNome());
        pessoaFisica.setCpf(contaDto.getPessoa().getCPFOrCNPJ());
        conta.setPessoa(pessoa);
        return conta;
    }
}
