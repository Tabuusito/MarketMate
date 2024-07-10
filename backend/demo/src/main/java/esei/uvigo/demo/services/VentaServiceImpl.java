package esei.uvigo.demo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esei.uvigo.demo.daos.DetalleVentaRepository;
import esei.uvigo.demo.daos.InventarioRepository;
import esei.uvigo.demo.daos.VentasRepository;
import esei.uvigo.demo.entities.DetalleVenta;
import esei.uvigo.demo.entities.Producto;
import esei.uvigo.demo.entities.Venta;
import esei.uvigo.demo.exceptions.NotFoundException;

@Service
public class VentaServiceImpl implements VentaService{

	
	@Autowired
	private VentasRepository ventaRepository;
	
	@Autowired
	private DetalleVentaRepository detalleVentaRepository;
	
	@Autowired
    private InventarioRepository productoRepository;
	
	@Autowired
	@Lazy
    private CajaService cajaService;
	
	public List<Venta> buscarTodas(){
		List<Venta> toret = new ArrayList<>();
		
		for (Venta v : ventaRepository.findAll()) {
			toret.add(v);
		}
		
		return toret;
	}
	
	public List<DetalleVenta> buscarTodos(){
		List<DetalleVenta> toret = new ArrayList<>();
		
		for (DetalleVenta d : detalleVentaRepository.findAll()) {
			toret.add(d);
		}
		
		return toret;
	}
	
	public Venta buscarPorClave(Integer id) throws NotFoundException{
		Optional<Venta> venta = ventaRepository.findById(id);
		
		if (venta.isPresent())
			return venta.get();
		else
			throw new NotFoundException();
	}
	
	public List<Venta> buscarPorFecha(LocalDateTime date){
	    List<Venta> ventasCoincidentes = new ArrayList<>();
	    LocalDate fechaBuscada = date.toLocalDate();

	    for (Venta v : ventaRepository.findAll()) {
	        LocalDate fechaVenta = v.getFechaHora().toLocalDate();

	        if (fechaVenta.equals(fechaBuscada)) {
	            ventasCoincidentes.add(v);
	        }
	    }

	    return ventasCoincidentes;
	}
	
	@Transactional
	public Venta crear(Venta venta){
	    venta.setId(null); // Si no se incluye esta línea los ids ya existentes pueden ser sobreescritos
	    Venta nuevaVenta = ventaRepository.save(venta);

	    for (DetalleVenta detalle : venta.getDetalles()) {
	        detalle.setId(null); // Mismo caso que con venta
	    	detalle.setVenta(nuevaVenta);

	        // Asigna y actualiza el producto
	        if (detalle.getProducto() != null && detalle.getProducto().getId() != null) {
	            Producto producto = productoRepository.findById(detalle.getProducto().getId())
	                               .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
	            
	            // Actualiza la cantidad en el inventario
	            int cantidadVendida = detalle.getCantidad();
	            producto.setCantidad(producto.getCantidad() - cantidadVendida);

	            // Guarda el producto actualizado en el repositorio
	            productoRepository.save(producto);

	            detalle.setProducto(producto);
	        }

	        detalleVentaRepository.save(detalle);
	    }
	    cajaService.actualizarCaja(LocalDateTime.now());

	    return nuevaVenta;
	}

}
