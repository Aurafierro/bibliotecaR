package com.gestion.gesion_bibliotec.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.gesion_bibliotec.interfaceService.IMultaService;
import com.gestion.gesion_bibliotec.models.multa;




@RequestMapping("/api/v1/multa")
@RestController
@CrossOrigin
public class multaController {

	  @Autowired
	    private IMultaService multaService;

	    @PostMapping("/")
	    public ResponseEntity<Object> save(@ModelAttribute("multa") multa multa) {
	        multaService.save(multa);
	        return new ResponseEntity<>(multa, HttpStatus.OK);
	    }

	    @GetMapping("/")
	    public ResponseEntity<Object> findAll() {
	        List<multa> listamulta = multaService.findAll();
	        return new ResponseEntity<>(listamulta, HttpStatus.OK);
	    }

	    @GetMapping("/{id_multa}")
	    public ResponseEntity<Object> findOne(@PathVariable String id_multa) {
	        var multa = multaService.findOne(id_multa);
	        if (multa.isPresent()) {
	            return new ResponseEntity<>(multa, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Multa no encontrada", HttpStatus.NOT_FOUND);
	        }
	    }

	    @PutMapping("/{id_multa}")
	    public ResponseEntity<Object> update(@PathVariable String id_multa, @ModelAttribute("multa") multa multaUpdate) {
	        var multa = multaService.findOne(id_multa);
	        if (multa.isPresent()) {
	            multa multaExistente = multa.get();

	            multaExistente.setValor_multa(multaUpdate.getValor_multa());
	            multaExistente.setUsuario(multaUpdate.getUsuario());
	            multaExistente.setPrestamo(multaUpdate.getPrestamo());
	            multaExistente.setFecha_multa(multaUpdate.getFecha_multa());
	            multaExistente.setEstado_multa(multaUpdate.getEstado_multa());

	            multaService.save(multaExistente);
	            return new ResponseEntity<>("Guardado", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Multa no encontrada", HttpStatus.NOT_FOUND);
	        }
	    }
	}