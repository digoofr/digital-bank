package br.com.digital.bank.api.repository;

import br.com.digital.bank.api.domain.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByNumeroConta(Long numeroConta);
}
