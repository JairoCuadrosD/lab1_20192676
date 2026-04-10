package com.example.demo.controller;

import com.example.demo.model.Pedido;
import com.example.demo.model.PedidoForm;
import com.example.demo.service.RestauranteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/*
He utilizado validación de formulario es por ello que importo
import org.springframework.validation.BindingResult;

https://adictosaltrabajo.com/2011/05/30/validator-form-spring-mvc/

 */
/*
  Pedidos: formulario y creación (data binding con {@link PedidoForm}).
 */
@Controller
@RequestMapping("/restaurante")
public class RestauranteCrearController {

    private final RestauranteService restauranteService;

    public RestauranteCrearController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        if (!model.containsAttribute("pedidoForm")) {
            model.addAttribute("pedidoForm", new PedidoForm());
        }
        model.addAttribute("menuAgrupado", restauranteService.getMenuAgrupado());
        return "restaurante/nuevo";
    }

    @PostMapping("/pedido")
    public String crear(@ModelAttribute("pedidoForm") PedidoForm form,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("menuAgrupado", restauranteService.getMenuAgrupado());
            return "restaurante/nuevo";
        }
        try {
            Pedido creado = restauranteService.crearPedido(form);
            redirectAttributes.addFlashAttribute("mensajeExito", "Pedido #" + creado.getId() + " registrado.");
            return "redirect:/restaurante/pedido/" + creado.getId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorNegocio", e.getMessage());
            model.addAttribute("menuAgrupado", restauranteService.getMenuAgrupado());
            return "restaurante/nuevo";
        }
    }

    /*https://labex.io/es/tutorials/java-how-to-handle-illegalargumentexception-in-java-417309
    indicará que el metodo ha recibido un argunento que no debería, asi como utilizamos en el ciclo anterior
     */
}
