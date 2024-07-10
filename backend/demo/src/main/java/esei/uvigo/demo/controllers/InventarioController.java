package esei.uvigo.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import esei.uvigo.demo.services.InventarioService;
import esei.uvigo.demo.services.StorageService;
import esei.uvigo.demo.exceptions.AlreadyExistsException;
import esei.uvigo.demo.exceptions.NotFoundException;
import esei.uvigo.demo.entities.Producto;

@RestController
public class InventarioController {

	@Autowired
	private InventarioService servicio;
	
	@Autowired
	private StorageService storageService;

	@GetMapping("/inventario/{id}")
	public ResponseEntity<Producto> buscarPorClave(@PathVariable("id") Integer id) {
		try {
			Producto producto = servicio.buscarPorClave(id);
			return new ResponseEntity<>(producto, HttpStatus.OK);
		}
		catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/inventario/ref/{referencia}")
	public ResponseEntity<Producto> buscarPorReferencia(@PathVariable("referencia") String referencia) {
		try {
			Producto producto = servicio.buscarPorReferencia(referencia);
			return new ResponseEntity<>(producto, HttpStatus.OK);
		}
		catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Evita conflicto al intentar matchear la ruta sin parametros en el frontend
	@GetMapping("/inventario/search/")
	public ResponseEntity<List<Producto>> busquedaVacia() {
		try {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/inventario/search/{expresion}")
	public ResponseEntity<List<Producto>> buscarPorNombreOReferencia(@PathVariable("expresion") String expresion) {
		try {
			List<Producto> listaProductos = servicio.buscarPorNombreOReferencia(expresion);
			
			if (listaProductos.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else
				return new ResponseEntity<>(listaProductos, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/inventario/{id}")
	public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Integer id){
		try {
			servicio.eliminar(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		}
		catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/inventario/{id}")
	public ResponseEntity<Producto> actualizar(@PathVariable("id") Integer id, @RequestBody Producto producto){
		try {
			servicio.actualizar(id, producto);
			return new ResponseEntity<>(producto, HttpStatus.OK); 
		}
		catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		catch (AlreadyExistsException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/inventario/")
	public ResponseEntity<Producto> crear (@RequestBody Producto producto){
		try {
			servicio.crear(producto);
			return new ResponseEntity<>(producto, HttpStatus.CREATED); 
		}
		catch (AlreadyExistsException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/inventario/{id}/imagen")
	public ResponseEntity<Producto> actualizarImagen(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
	    try {
	    	System.out.println("hello from controller");
	        Producto producto = servicio.buscarPorClave(id);
	        String imageUrl = storageService.store(file);
	        producto.setImagen(imageUrl);
	        System.out.println(servicio.actualizar(id, producto));
	        return new ResponseEntity<>(producto, HttpStatus.OK); 
	    } catch (NotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	    	System.err.println("Exception " + e.getMessage());
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
