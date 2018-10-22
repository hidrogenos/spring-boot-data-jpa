package com.bolsadeideas.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.model.entity.Cliente;
import com.bolsadeideas.springboot.app.model.entity.Factura;
import com.bolsadeideas.springboot.app.model.entity.Producto;
import com.bolsadeideas.springboot.app.model.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(name="clienteId") Long clienteId, 
			Map<String, Object> model, 
			RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findOne(clienteId);
		
		if(cliente == null) {
			flash.addAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		
		Factura factura = new Factura();
		factura.setCliente(cliente);
		
		model.put("factura", factura);
		model.put("titulo", "Crear factura");
		
		return "factura/form";
		
	}
	
	@GetMapping(value="/carga-productos/{term}", produces= {"application/json"})
	public @ResponseBody List<Producto> cargarProductos(@PathVariable(name="term") String term){
		return clienteService.findbyNombre(term);
	}
	
}
