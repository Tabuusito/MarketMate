package esei.uvigo.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import esei.uvigo.demo.daos.CajaRepository;
import esei.uvigo.demo.entities.Caja;
import esei.uvigo.demo.entities.Venta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CajaServiceImpl implements CajaService {

    @Autowired
    private CajaRepository cajaRepository;

    @Autowired
    @Lazy
    private VentaService ventaService;
    
    public List<Caja> buscarPorMes(LocalDateTime fecha) {
        LocalDate fechaInicioMes = fecha.withDayOfMonth(1).toLocalDate();
        LocalDate fechaFinMes = fecha.withDayOfMonth(fecha.toLocalDate().lengthOfMonth()).toLocalDate();
        
        List<Caja> cajasDelMes = new ArrayList<>();
        
        for (Caja caja : cajaRepository.findAll()) {
            LocalDate fechaCaja = caja.getFecha().toLocalDate();
            if (!fechaCaja.isBefore(fechaInicioMes) && !fechaCaja.isAfter(fechaFinMes)) {
                cajasDelMes.add(caja);
            }
        }
        
        return cajasDelMes;
    }
    
    public Caja actualizarCaja(LocalDateTime fecha) {
        Optional<Caja> cajaExistenteOp = findByFecha(fecha);
        
        List<Venta> ventasDelDia = ventaService.buscarPorFecha(fecha);
        if (ventasDelDia.isEmpty()) {
            if (cajaExistenteOp.isPresent()) {
                return cajaExistenteOp.get();
            } else {
                Caja caja = new Caja();
                caja.setFecha(fecha);
                caja.setPrimerTicket(new Venta());
                caja.setUltimoTicket(new Venta());
                caja.setTotal(0.0);
                
                cajaRepository.save(caja);
                return caja;
            }
        }

        Venta primerTicket = ventasDelDia.get(0);
        Venta ultimoTicket = ventasDelDia.get(0); 
        Double montoTotal = 0.0;
        
        for (Venta venta : ventasDelDia) {
        	if (venta.getId() < primerTicket.getId()) {
                primerTicket = venta;
            }
            if (venta.getId() > ultimoTicket.getId()) {
                ultimoTicket = venta;
            }
            montoTotal += venta.getTotal();
        }

        Caja caja;
        if (cajaExistenteOp.isPresent()) {
            caja = cajaExistenteOp.get();
        } else {
            caja = new Caja();
            caja.setFecha(fecha);
        }
        caja.setPrimerTicket(primerTicket);
        caja.setUltimoTicket(ultimoTicket);
        caja.setTotal(montoTotal);

        cajaRepository.save(caja);
        
        return caja;
    }

    
    public Optional<Caja> findByFecha(LocalDateTime fecha) {
        for (Caja caja : cajaRepository.findAll()) {
            if (caja.getFecha().toLocalDate().equals(fecha.toLocalDate())) {
                return Optional.of(caja);
            }
        }
        return Optional.empty();
    }
}
