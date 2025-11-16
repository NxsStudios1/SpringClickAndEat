package com.example.demo.model.pedido;

import com.example.demo.model.base.Entidad;
import com.example.demo.model.sesion.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_pedido")
public class Pedido extends Entidad {

    @Column(nullable = false, unique = true, length = 20)
    private String numeroTicket;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedidoEnum estado;

    @Column(nullable = false)
    private Double total;

    @Column(name = "fechaPedido", updatable = false)
    private LocalDateTime fechaPedido;

    @Column(length = 500)
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detalles;

    @PrePersist
    public void prePersist() {
        if (fechaPedido == null) {
            fechaPedido = LocalDateTime.now();
        }
    }
}
