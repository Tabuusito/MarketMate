package esei.uvigo.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import esei.uvigo.demo.entities.DetalleVenta;
import esei.uvigo.demo.entities.Venta;
import esei.uvigo.demo.exceptions.NotFoundException;

@Service
public interface VentaService {

	public List<Venta> buscarTodas();
	
	public List<DetalleVenta> buscarTodos();
	
	public Venta buscarPorClave(Integer id) throws NotFoundException;	
	
	public List<Venta> buscarPorFecha(LocalDateTime date);
	
	public Venta crear(Venta v);
	
}
