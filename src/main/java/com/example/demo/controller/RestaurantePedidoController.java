package com.example.demo.controller;

import com.example.demo.service.RestauranteService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Resumen de un pedido, avance de estado e historial.
 */
@Controller
@RequestMapping("/restaurante")
public class RestaurantePedidoController {

    private final RestauranteService restauranteService;

    public RestaurantePedidoController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @GetMapping("/pedido/{id}")
    public String resumen(@PathVariable Long id, Model model) {
        return restauranteService.buscarPedido(id)
                .map(p -> {
                    model.addAttribute("pedido", p);
                    return "restaurante/resumen";
                })
                .orElse("redirect:/restaurante/nuevo");
    }

    @PostMapping("/pedido/{id}/avanzar")
    public String avanzarEstado(@PathVariable Long id) {
        restauranteService.avanzarEstado(id);
        return "redirect:/restaurante/pedido/" + id;
    }

    @GetMapping("/historial")
    public String historial(Model model) {
        model.addAttribute("pedidos", restauranteService.getHistorial());
        return "restaurante/historial";
    }
}
