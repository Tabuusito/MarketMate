package esei.uvigo.demo.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import esei.uvigo.demo.entities.DetalleVenta;
import esei.uvigo.demo.entities.Venta;
import esei.uvigo.demo.services.VentaService;

@RestController
@RequestMapping("/ventas")
public class VentasController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/buscarPorFecha")
    public ResponseEntity<List<Venta>> buscarPorFecha(@RequestParam("fecha") String fecha) {
        LocalDateTime fechaBuscada;
        try {
            fechaBuscada = LocalDate.parse(fecha).atStartOfDay();
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Venta> ventas = ventaService.buscarPorFecha(fechaBuscada);
        return ResponseEntity.ok(ventas);
    }
    @GetMapping("/detalles")
    public ResponseEntity<List<DetalleVenta>> buscarDetalles() {
        List<DetalleVenta> detalles = ventaService.buscarTodos();
        return ResponseEntity.ok(detalles);
    }
    
    @PostMapping("/crear")
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
        try {
            Venta nuevaVenta = ventaService.crear(venta);
            return ResponseEntity.ok(nuevaVenta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
