package esei.uvigo.demo.daos;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import esei.uvigo.demo.entities.Producto;

@Repository
public interface InventarioRepository extends CrudRepository<Producto, Integer>{

}
