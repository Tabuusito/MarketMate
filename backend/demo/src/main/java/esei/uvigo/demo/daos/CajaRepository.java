package esei.uvigo.demo.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import esei.uvigo.demo.entities.Caja;

@Repository
public interface CajaRepository extends CrudRepository<Caja, Integer>{

}
