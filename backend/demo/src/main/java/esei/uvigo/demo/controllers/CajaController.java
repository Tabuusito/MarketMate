package esei.uvigo.demo.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import esei.uvigo.demo.entities.Caja;
import esei.uvigo.demo.services.CajaService;

@RestController
@RequestMapping("/cajas")
public class CajaController {

	@Autowired
    private CajaService cajaService;

	@GetMapping
	public ResponseEntity<List<Caja>> buscarPorMes(@RequestParam("fecha") String fecha){
		LocalDateTime fechaBuscada;
        try {
            fechaBuscada = LocalDate.parse(fecha).atStartOfDay();
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Caja> cajas = cajaService.buscarPorMes(fechaBuscada);
        return ResponseEntity.ok(cajas);
	}

}
