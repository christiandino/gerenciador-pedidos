package br.ce.christian.repository;

import br.ce.christian.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p JOIN FETCH p.produtos")
    List<Pedido> findAllWithProdutos();

    List<Pedido> findByDataGreaterThanEqual(LocalDate dataInicio);

    List<Pedido> findByDataLessThanEqual(LocalDate dataInicio);

    List<Pedido> findByDataIsNull();

    List<Pedido> findByDataIsNotNull();

    List<Pedido> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

    @Query("SELECT p FROM Pedido p WHERE p.data BETWEEN :dataInicio AND :dataFim")
    List<Pedido> buscarPedidosPorPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

    List<Pedido> findByClienteId(Long clienteId);
}
