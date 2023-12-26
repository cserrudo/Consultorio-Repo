package com.example.springbootjpa.model.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paciente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente",cascade = CascadeType.ALL)
    private List<Turno> turnos = new ArrayList<>();

    public void addTurno(Turno t){
        this.turnos.add(new Turno(t.getId(),t.getConsultorio(),this,t.getProfesional(), t.getFecha_hora_turno()));
    }
}