package esei.uvigo.demo.daos;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import esei.uvigo.demo.entities.Venta;

@Repository
public interface VentasRepository extends CrudRepository <Venta, Integer>{

}
