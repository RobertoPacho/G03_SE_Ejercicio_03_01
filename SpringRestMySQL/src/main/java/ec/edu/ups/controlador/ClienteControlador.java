package ec.edu.ups.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.repositorio.ClienteRepositorio;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ClienteControlador {

	@Autowired
	ClienteRepositorio repository;

	@GetMapping("/customers")
	public List<Cliente> getAllCustomers() {
		System.out.println("Obtener datos de los clientes...");

		List<Cliente> clientes = new ArrayList<>();
		repository.findAll().forEach(clientes::add);

		return clientes;
	}

	@PostMapping(value = "/customers/create")
	public Cliente postCustomer(@RequestBody Cliente cliente) {

		Cliente _customer = repository.save(new Cliente(cliente.getName(), cliente.getAge()));
		return _customer;
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") long id) {
		System.out.println("Eliminar cliente con id= " + id + "...");

		repository.deleteById(id);

		return new ResponseEntity<>("El cliente ha sido eliminado", HttpStatus.OK);
	}

	@DeleteMapping("/customers/delete")
	public ResponseEntity<String> deleteAllCustomers() {
		System.out.println("Eliminar todos los clientes");

		repository.deleteAll();

		return new ResponseEntity<>("Todos los clientes han sido eliminados", HttpStatus.OK);
	}

	@GetMapping(value = "customers/age/{age}")
	public List<Cliente> findByAge(@PathVariable int age) {

		List<Cliente> clientes = repository.findByAge(age);
		return clientes;
	}

	@PutMapping("/customers/{id}")
	public ResponseEntity<Cliente> updateCustomer(@PathVariable("id") long id, @RequestBody Cliente cliente) {
		System.out.println("Actualizar cliente con id= " + id + "...");

		Optional<Cliente> customerData = repository.findById(id);

		if (customerData.isPresent()) {
			Cliente _customer = customerData.get();
			_customer.setName(cliente.getName());
			_customer.setAge(cliente.getAge());
			_customer.setActive(cliente.isActive());
			return new ResponseEntity<>(repository.save(_customer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
