package esei.uvigo.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import esei.uvigo.demo.entities.Caja;

@Service
public interface CajaService {
	
	public List<Caja> buscarPorMes(LocalDateTime date);

	public Caja actualizarCaja(LocalDateTime date);
}
