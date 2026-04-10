package com.example.demo.service;

import com.example.demo.model.Dish;
import com.example.demo.model.DishType;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.Pedido;
import com.example.demo.model.PedidoForm;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    private final List<Dish> menu = new ArrayList<>();
    private final Map<Long, Pedido> pedidos = new ConcurrentHashMap<>();
    private final AtomicLong siguienteIdPedido = new AtomicLong(1);
    private final AtomicLong siguienteIdPlato = new AtomicLong(1);

    @PostConstruct
    public void cargarMenu() {
        agregarPlato("Tequeños (6 uds)", 12.0, 10, DishType.ENTRADA);
        agregarPlato("Ensalada mixta", 8.50, 8, DishType.ENTRADA);
        agregarPlato("Lomo saltado", 28.0, 22, DishType.PLATO_PRINCIPAL);
        agregarPlato("Ají de gallina", 24.0, 20, DishType.PLATO_PRINCIPAL);
        agregarPlato("Chicha morada (jarra)", 9.0, 3, DishType.BEBIDA);
        agregarPlato("Limonada fresca", 7.50, 4, DishType.BEBIDA);
    }

    private void agregarPlato(String nombre, double precio, int minutos, DishType tipo) {
        long id = siguienteIdPlato.getAndIncrement();
        menu.add(new Dish(id, nombre, precio, minutos, tipo));
    }

    public List<Dish> getMenu() {
        return List.copyOf(menu);
    }

    public List<Dish> getMenuPorTipo() {
        return menu.stream()
                .sorted(Comparator.comparing(Dish::getTipo).thenComparing(Dish::getNombre))
                .collect(Collectors.toList());
    }

    public Map<DishType, List<Dish>> getMenuAgrupado() {
        Map<DishType, List<Dish>> map = new EnumMap<>(DishType.class);
        for (DishType t : DishType.values()) {
            map.put(t, new ArrayList<>());
        }
        for (Dish d : menu) {
            map.get(d.getTipo()).add(d);
        }
        return map;
    }

    public Optional<Dish> buscarPlato(Long id) {
        return menu.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public Pedido crearPedido(PedidoForm form) {
        List<Dish> elegidos = new ArrayList<>();
        for (Long id : form.getPlatosIds()) {
            buscarPlato(id).ifPresent(elegidos::add);
        }
        if (elegidos.isEmpty()) {
            throw new IllegalArgumentException("No hay platos válidos en el pedido");
        }
        int tiempoTotal = elegidos.stream().mapToInt(Dish::getMinutosPreparacion).sum();

        Pedido pedido = new Pedido();
        pedido.setId(siguienteIdPedido.getAndIncrement());
        pedido.setNombreCliente(form.getNombreCliente().trim());
        pedido.setTipoServicio(form.getTipoServicio());
        pedido.setPlatos(elegidos);
        pedido.setEstado(OrderStatus.CREADO);
        pedido.setTiempoTotalMinutos(tiempoTotal);

        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    public Optional<Pedido> buscarPedido(Long id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    public List<Pedido> getHistorial() {
        return pedidos.values().stream()
                .sorted(Comparator.comparing(Pedido::getId).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Avanza un paso en el flujo de estados (manual, desde botón).
     */
    public boolean avanzarEstado(Long idPedido) {
        Pedido p = pedidos.get(idPedido);
        if (p == null) {
            return false;
        }
        OrderStatus actual = p.getEstado();
        switch (actual) {
            case CREADO -> p.setEstado(OrderStatus.EN_PREPARACION);
            case EN_PREPARACION -> p.setEstado(OrderStatus.LISTO);
            case LISTO -> p.setEstado(OrderStatus.ENTREGADO);
            default -> {
                return false;
            }
        }
        return true;
    }
}
