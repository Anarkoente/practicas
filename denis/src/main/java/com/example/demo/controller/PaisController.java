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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import com.example.demo.entity.Pais;
import com.example.demo.service.PaisService;

@RestController
@RequestMapping("/paises")


public class PaisController  {
	@Autowired
	@Qualifier("paisservice")
	private PaisService paisservice;
	
	@GetMapping("/paises/listapaises")
	public ModelAndView listAllPaises() {

	    List<Pais> pais = paisservice.listAllPaises();

	    for(Pais paises : pais) {
	        System.out.println(paises.getPais());
	    }

		ModelAndView mav = new ModelAndView("list");
		mav.addObject("paises", pais);
		return mav;
	}

	
	// Create a new mensaje

	@GetMapping ("/añadirpais")

    	public ModelAndView formularioPaises () {
    		ModelAndView mav = new ModelAndView("crear");
    		mav.addObject("paisNuevo", new Pais());
            return mav;
    	}
	
	@PostMapping ("/añadirpais")

	public ModelAndView addPais (@ModelAttribute(name="pais")Pais pais) {
		paisservice.addpais(pais);
		String urlInterna = "/paises/listapaises";
        return new ModelAndView("redirect:"+urlInterna);
	}
	
	public ResponseEntity<?> create (@RequestBody Pais pais){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(paisservice.save(pais));
		
		
	}

	//Read an mensaje
	@GetMapping("/{id}")
	public ModelAndView read (@PathVariable(value = "id") Long paisId){

		Optional<Pais> oPaises = paisservice.findById(paisId);

		if(!oPaises.isPresent()) {
            ModelAndView mav404 = new ModelAndView("404");
            return mav404;
		}

		ModelAndView mav = new ModelAndView("editar");
        mav.addObject("pais", oPaises);
        return mav;
	}
	
	//Update an mensaje
	
	@PostMapping("/{id}/editarpais")
	public ModelAndView update(@RequestParam(value = "pais", required = false) String pais, @PathVariable(value = "id") Long paisId){
		Optional<Pais> paises = paisservice.findById(paisId);

		if(!paises.isPresent()) {
			ModelAndView mav404 = new ModelAndView("404");
            return mav404;
		}

		paises.get().setPais(pais);
		paisservice.save(paises.get());

        String urlInterna = "/paises/listapaises";
        return new ModelAndView("redirect:"+urlInterna);
	}
	
	//Delete an Mensajes
	
	@PostMapping("/{id}/borrar")
	
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long paisId){
		if (!paisservice.findById(paisId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		paisservice.deleteById(paisId);
		return ResponseEntity.ok().build();
	}

	//Read all mensajes
	@GetMapping
	
	public List<Pais> readall() {
		
		List<Pais> pais = StreamSupport
				.stream(paisservice.findAll().spliterator(), false)
				.collect(Collectors.toList());
		
		return pais;
	}
	
}
