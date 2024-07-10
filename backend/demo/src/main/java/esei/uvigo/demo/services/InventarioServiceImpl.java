package esei.uvigo.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esei.uvigo.demo.daos.InventarioRepository;
import esei.uvigo.demo.entities.Producto;
import esei.uvigo.demo.exceptions.AlreadyExistsException;
import esei.uvigo.demo.exceptions.NotFoundException;

@Service
public class InventarioServiceImpl implements InventarioService{

	@Autowired
	private InventarioRepository repo;
	
	public Producto buscarPorClave(Integer id) throws NotFoundException{
		Optional<Producto> producto = repo.findById(id);
		
		if (producto.isPresent())
			return producto.get();
		else
			throw new NotFoundException();
	}
	
	public Producto buscarPorReferencia(String expresion) throws NotFoundException{

        for (Producto producto : repo.findAll()) {
            if (producto.getReferencia().toLowerCase().equals(expresion.toLowerCase())) {
                return producto;
            }
        }

        throw new NotFoundException();
    }
	
	public List<Producto> buscarPorNombre (String expresion) {
        List<Producto> resultado = new ArrayList<>();

        for (Producto producto : repo.findAll()) {
            if (producto.getNombre().toLowerCase().contains(expresion.toLowerCase())) {
                resultado.add(producto);
            }
        }

        return resultado;
    }
	
	public List<Producto> buscarPorNombreOReferencia(String expresion) {
	    List<Producto> resultado = new ArrayList<>();

	    for (Producto producto : repo.findAll()) {
		    if (producto.getNombre().toLowerCase().contains(expresion.toLowerCase()) ||
		        producto.getReferencia().toLowerCase().contains(expresion.toLowerCase())) {
		        resultado.add(producto);
		    }
		}    

	    return resultado;
	}

	
	public void eliminar(Integer id) throws NotFoundException {
		Optional<Producto> producto = repo.findById(id);
		
		if (producto.isPresent())
			repo.delete(producto.get());
		else
			throw new NotFoundException();
	}
	
	public Producto actualizar(Integer id, Producto nuevoProducto) throws NotFoundException, AlreadyExistsException {
	    Optional<Producto> productoExistente = repo.findById(id);

	    if (productoExistente.isPresent()) {
	        Producto productoActual = productoExistente.get();

	        // Comprobar si la referencia es la misma que la del producto existente
	        if (!productoActual.getReferencia().equalsIgnoreCase(nuevoProducto.getReferencia())) {
	            // Si la referencia es diferente, comprobar si ya existe en la base de datos
	            if (existeReferencia(nuevoProducto.getReferencia())) {
	                throw new AlreadyExistsException();
	            }
	        }

	        productoActual.setNombre(nuevoProducto.getNombre());
	        productoActual.setReferencia(nuevoProducto.getReferencia());
	        productoActual.setCantidad(nuevoProducto.getCantidad());
	        productoActual.setFamilia(nuevoProducto.getFamilia());
	        productoActual.setPrecio(nuevoProducto.getPrecio());

	        return repo.save(productoActual);
	    } else {
	        throw new NotFoundException();
	    }
	}

	
	public Producto crear(Producto nuevoProducto) throws AlreadyExistsException{
		Optional<Producto> producto = repo.findById(nuevoProducto.getId());

		if (producto.isEmpty())
			if(!existeReferencia(nuevoProducto.getReferencia()))
				return repo.save(nuevoProducto);
			else
				throw new AlreadyExistsException();
		else
			throw new AlreadyExistsException();
	}
	
	//Devuelve True si la referencia ya existe
	public boolean existeReferencia(String referencia) {
		for (Producto producto : repo.findAll()) {
			if (producto.getReferencia().equalsIgnoreCase(referencia))
				return true;
		}		
		return false;
	}
}
