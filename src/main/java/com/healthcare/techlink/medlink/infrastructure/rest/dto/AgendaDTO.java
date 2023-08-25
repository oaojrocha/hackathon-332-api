package com.healthcare.techlink.medlink.infrastructure.rest.dto;

import java.util.Date;

public class AgendaDTO {

    private Date dataConsulta;
    private long idMedico;
    private long idPaciente;
    
    public Date getDataConsulta() {
        return dataConsulta;
    }
    public void setDataConsulta(Date dataConsulta) {
        this.dataConsulta = dataConsulta;
    }
    public long getIdMedico() {
        return idMedico;
    }
    public void setIdMedico(long idMedico) {
        this.idMedico = idMedico;
    }
    public long getIdPaciente() {
        return idPaciente;
    }
    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    
}