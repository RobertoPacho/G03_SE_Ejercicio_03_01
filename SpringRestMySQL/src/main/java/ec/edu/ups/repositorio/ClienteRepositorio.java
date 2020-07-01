package ec.edu.ups.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ec.edu.ups.modelo.Cliente;

public interface ClienteRepositorio extends CrudRepository<Cliente, Long> {
	List<Cliente> findByAge(int age);
}
