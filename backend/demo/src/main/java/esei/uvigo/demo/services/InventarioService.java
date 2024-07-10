package esei.uvigo.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import esei.uvigo.demo.entities.Producto;
import esei.uvigo.demo.exceptions.AlreadyExistsException;
import esei.uvigo.demo.exceptions.NotFoundException;

@Service
public interface InventarioService {
	
	public Producto buscarPorClave(Integer id) throws NotFoundException;
	
	public Producto buscarPorReferencia(String referencia) throws NotFoundException;
	
	public List<Producto> buscarPorNombre (String expresion);
	
	public List<Producto> buscarPorNombreOReferencia(String expresion);
	
	public void eliminar(Integer id) throws NotFoundException;
	
	public Producto actualizar(Integer id, Producto p) throws NotFoundException, AlreadyExistsException;
	
	public Producto crear(Producto p) throws AlreadyExistsException;
	
}
