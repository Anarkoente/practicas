package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import com.example.demo.entity.Mensajes;
import com.example.demo.service.MensajesService;

@RestController
@RequestMapping("/mensajes")


public class MensajesController  {
	@Autowired
	@Qualifier("mensajesservice")
	private MensajesService mensajesservice;
	
	@GetMapping("/list")
	public ModelAndView listAllMensajes() {

	    List<Mensajes> mensajes = mensajesservice.listAllMensajes();

	    for(Mensajes mensaje : mensajes) {
	        System.out.println(mensaje.getPais());
	    }

		ModelAndView mav = new ModelAndView("list");
		mav.addObject("mensajes", mensajes);
		return mav;
	}

	
	// Create a new mensaje

	@GetMapping ("/añadir")

    	public ModelAndView formularioMensajes () {
    		ModelAndView mav = new ModelAndView("crear");
    		mav.addObject("mensajeNuevo", new Mensajes());
            return mav;
    	}
	
	@PostMapping ("/añadir")

	public ModelAndView addMensajes (@ModelAttribute(name="mensajes1")Mensajes mensaje) {
		mensajesservice.addmensajes(mensaje);
		String urlInterna = "/mensajes/list";
        return new ModelAndView("redirect:"+urlInterna);
	}
	
	public ResponseEntity<?> create (@RequestBody Mensajes mensajes){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(mensajesservice.save(mensajes));
		
		
	}

	//Read an mensaje
	@GetMapping("/{id}")
	public ResponseEntity<?> read (@PathVariable(value = "id") Long mensajesId){
		
		Optional<Mensajes> oMensajes = mensajesservice.findById(mensajesId);
		
		if(!oMensajes.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oMensajes);
	}
	
	//Update an mensaje
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Mensajes mensajesDetails, @PathVariable(value = "id") Long mensajesId){
		Optional<Mensajes> mensajes = mensajesservice.findById(mensajesId);
		
		if(!mensajes.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		mensajes.get().setPais(mensajesDetails.getPais());
		mensajes.get().setMensajes(mensajesDetails.getMensajes());


		
		return ResponseEntity.status(HttpStatus.CREATED).body(mensajesservice.save(mensajes.get()));
	}
	
	//Delete an Mensajes
	
	@DeleteMapping("/{id}")
	
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long mensajeId){
		if (!mensajesservice.findById(mensajeId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		mensajesservice.deleteById(mensajeId);
		return ResponseEntity.ok().build();
	}

	//Read all mensajes
	@GetMapping
	
	public List<Mensajes> readall() {
		
		List<Mensajes> mensajes = StreamSupport
				.stream(mensajesservice.findAll().spliterator(), false)
				.collect(Collectors.toList());
		
		return mensajes;
	}
	
}
