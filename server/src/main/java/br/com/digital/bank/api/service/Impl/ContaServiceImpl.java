package br.com.digital.bank.api.service.Impl;

import br.com.digital.bank.api.domain.Conta;
import br.com.digital.bank.api.exception.DomainException;
import br.com.digital.bank.api.exception.ResourceNotFoundException;
import br.com.digital.bank.api.repository.ContaRepository;
import br.com.digital.bank.api.service.ContaService;
import br.com.digital.bank.api.utils.CPFCNPJUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static br.com.digital.bank.api.utils.VariaveisUtils.*;

@Service
public class ContaServiceImpl implements ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Override
    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    @Override
    public Conta create(Conta conta) {
        return contaRepository.save(conta);
    }

    @Override
    public Conta findById(Long id) {
        return contaRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(MSG_NUMERO_CONTA_NAO_EXISTENTE));
    }

    @Override
    public Conta findByNumeroConta(Long numeroConta) {
        return contaRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new ResourceNotFoundException(MSG_NUMERO_CONTA_NAO_EXISTENTE));
    }

    @Override
    public void deleteById(Long id) {
        contaRepository.deleteById(id);
    }

    @Override
    public boolean verificarLimiteAoCriarConta(Conta conta) {
        if (conta.getSaldo() >= VALOR_MINIMO_NOVA_CONTA) {
            return true;
        } else {
            throw new DomainException(MSG_SALDO_INSUFICIENTE_NOVA_CONTA);
        }
    }

    @Override
    public boolean verificarCpfVazio(String cpf) {
        if (cpf.isEmpty() || cpf == null) {
            throw new DomainException(MSG_CPF_VAZIO);
        }
        return false;
    }

    @Override
    public boolean verificarCpfInvalido(String cpf) {
        if (!CPFCNPJUtils.isCpfValido(cpf)) {
            throw new DomainException(MSG_CPF_INVALIDO);
        }
        return false;
    }

    @Override
    public boolean verificarLimiteMinimo(Double valor) {
        if (valor <= VALOR_MAXIMO_TRANSFERENCIA) {
            return true;
        } else {
            throw new DomainException(MSG_LIMITE_DE_OPERACAO);
        }
    }

    @Override
    public boolean vereficarSaldoNaConta(Conta conta, Double valor) {
        if (valor <= conta.getSaldo()) {
            return true;
        } else {
            throw new DomainException(MSG_SALDO_INSUFICIENTE);
        }
    }

    @Override
    public Conta depositar(Conta conta, Double valor) {
        conta.setSaldo(conta.getSaldo() + valor);
        return contaRepository.save(conta);
    }

    @Override
    public Conta sacar(Conta conta, Double valor) {
        conta.setSaldo(conta.getSaldo() - valor);
        return contaRepository.save(conta);
    }

    @Override
    public Long gerarNumeroDaConta() {
        Random random = new Random();
        long numeroDaConta = random.nextInt(100000);
        return numeroDaConta;
    }

    @Override
    public List<Conta> transferir(Conta contaSolicitante, Conta contaBeneficiario, Double valor) {
        List<Conta> listaDeContas = new ArrayList<>();
        contaSolicitante.setSaldo(contaSolicitante.getSaldo() - valor);
        contaBeneficiario.setSaldo(contaBeneficiario.getSaldo() + valor);
        listaDeContas.add(contaSolicitante);
        listaDeContas.add(contaBeneficiario);
        contaRepository.saveAll(listaDeContas);
        return listaDeContas;
    }
}
