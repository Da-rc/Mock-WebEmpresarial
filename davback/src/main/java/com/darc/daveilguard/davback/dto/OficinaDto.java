package com.darc.daveilguard.davback.dto;

import com.darc.daveilguard.davback.model.Empleado;

import java.util.List;

public class OficinaDto {
    private int id;
    private String provincia;
    private String pais;
    private List<Empleado> empleados;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
}
