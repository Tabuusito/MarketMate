package esei.uvigo.demo.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import esei.uvigo.demo.entities.DetalleVenta;

@Repository
public interface DetalleVentaRepository extends CrudRepository <DetalleVenta, Long>{

}
