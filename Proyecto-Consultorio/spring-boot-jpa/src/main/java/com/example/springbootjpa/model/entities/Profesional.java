package com.example.springbootjpa.model.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;


@Entity
@Table(name = "profesional")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profesional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profesional",cascade = CascadeType.ALL)
    private List<Turno> turnos;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad")
    private Especialidad especialidad;
    @Column(name = "horario_inicio")
    private LocalTime horario_inicio;
    @Column(name = "horario_fin")
    private LocalTime horario_fin;

    public void addTurno(Turno t){
        this.turnos.add(new Turno(t.getId(),t.getConsultorio(),t.getPaciente(),this, t.getFecha_hora_turno()));
    }
}