package com.example.springbootjpa.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "especialidad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "especialidad",cascade = CascadeType.ALL)
    private List<Profesional> profesionales = new ArrayList<>();
    @Column(name = "descripcion")
    private String descripcion;

public void addProfesional(Profesional p){
        this.profesionales.add(new Profesional(p.getId(),p.getNombre(),p.getApellido(),p.getTurnos(),this,p.getHorario_inicio(),p.getHorario_fin()));
}

}