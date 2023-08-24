package com.healthcare.techlink.medlink.core.init;

import com.healthcare.techlink.medlink.core.domain.HistoricoPaciente;
import com.healthcare.techlink.medlink.core.domain.Medico;
import com.healthcare.techlink.medlink.core.domain.Paciente;
import com.healthcare.techlink.medlink.core.repository.HistoricoPacienteRepository;
import com.healthcare.techlink.medlink.core.repository.MedicoRepository;
import com.healthcare.techlink.medlink.core.repository.PacienteRepository;

import java.util.Date;

public class Init {

    public void initEntities() {
        initPaciente();
        initMedico();
        initHistoricoPaciente();
    }

    Paciente p, p1, p2, p3, p4;
    HistoricoPaciente h, h1, h2, h3, h4;
    Medico m, m1, m2, m3, m4;

    private void initPaciente() {
        p = new Paciente();
        p.setId(1);
        p.setNome("João Ricardo Rocha");
        p.setDataNascimento(new Date());

        p1 = new Paciente();
        p1.setId(2);
        p1.setNome("João Ricardo Rocha");
        p1.setDataNascimento(new Date());

        p2 = new Paciente();
        p2.setId(3);
        p2.setNome("João Ricardo Rocha");
        p2.setDataNascimento(new Date());

        p3 = new Paciente();
        p3.setId(4);
        p3.setNome("João Ricardo Rocha");
        p3.setDataNascimento(new Date());

        p4 = new Paciente();
        p4.setId(5);
        p4.setNome("João Ricardo Rocha");
        p4.setDataNascimento(new Date());

        PacienteRepository.dados.add(p);
        PacienteRepository.dados.add(p1);
        PacienteRepository.dados.add(p2);
        PacienteRepository.dados.add(p3);
        PacienteRepository.dados.add(p4);
    }


    private void initHistoricoPaciente() {


        h = new HistoricoPaciente();
        h.setDataConsulta(new Date());
        h.setMedico(m);
        h.setPaciente(p);
        h.setDiagnostico("Febre");
        h.setTratamento("Repouso e hidratação");

        h1 = new HistoricoPaciente();
        h1.setDataConsulta(new Date());
        h1.setMedico(m2);
        h1.setPaciente(p1);
        h1.setDiagnostico("Dor de garganta");
        h1.setTratamento("Prescrição de antibióticos");

        h2 = new HistoricoPaciente();
        h2.setDataConsulta(new Date());
        h2.setMedico(m1);
        h2.setPaciente(p2);
        h2.setDiagnostico("Fratura no pé");
        h2.setTratamento("Gesso e fisioterapia");

        h3 = new HistoricoPaciente();
        h3.setDataConsulta(new Date());
        h3.setMedico(m3);
        h3.setPaciente(p);
        h3.setDiagnostico("Alergia cutânea");
        h3.setTratamento("Uso de antialérgicos");

        h4 = new HistoricoPaciente();
        h4.setDataConsulta(new Date());
        h4.setMedico(m1);
        h4.setPaciente(p4);
        h4.setDiagnostico("Hérnia de disco");
        h4.setTratamento("Fisioterapia e repouso");

        HistoricoPacienteRepository.dados.add(h);
        HistoricoPacienteRepository.dados.add(h1);
        HistoricoPacienteRepository.dados.add(h2);
        HistoricoPacienteRepository.dados.add(h3);
        HistoricoPacienteRepository.dados.add(h4);
    }

    private void initMedico() {

        m = new Medico();
        m.setNome("Dr. João");
        m.setEspecialidade("Cardiologista");
        m.setIdade(35);
        m.setHospital("Hospital Central");

        m1 = new Medico();
        m1.setNome("Dra. Maria");
        m1.setEspecialidade("Pediatra");
        m1.setIdade(28);
        m1.setHospital("Hospital Infantil");

        m2 = new Medico();
        m2.setNome("Dr. André");
        m2.setEspecialidade("Oftalmologista");
        m2.setIdade(40);
        m2.setHospital("Hospital de Olhos");

        m3 = new Medico();
        m3.setNome("Dra. Ana");
        m3.setEspecialidade("Ginecologista");
        m3.setIdade(32);
        m3.setHospital("Hospital da Mulher");

        m4 = new Medico();
        m4.setNome("Dr. Paulo");
        m4.setEspecialidade("Dentista");
        m4.setIdade(45);
        m4.setHospital("Consultório Odontológico");

        MedicoRepository.dados.add(m);
        MedicoRepository.dados.add(m1);
        MedicoRepository.dados.add(m2);
        MedicoRepository.dados.add(m3);
        MedicoRepository.dados.add(m4);

    }

}
