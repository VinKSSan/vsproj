package com.clients.vsproj.dto;

import com.clients.vsproj.entities.Client;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public class ClientDTO {
    private Long id;
    @NotBlank(message = "nome não pode estar vazio!")
    private String name;
    private String cpf;
    private Double income;
    @PastOrPresent(message = "não é possivel inserir data futura!")
    private LocalDate birthDate;
    private Integer children;

    public  ClientDTO(){}

    public ClientDTO(Client c){
        id = c.getId();
        name = c.getName();
        cpf = c.getCpf();
        income = c.getIncome();
        birthDate = c.getBirthDate();
        children = c.getChildren();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public Double getIncome() {
        return income;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getChildren() {
        return children;
    }
}
