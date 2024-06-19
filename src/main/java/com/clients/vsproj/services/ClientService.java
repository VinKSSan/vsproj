package com.clients.vsproj.services;

import com.clients.vsproj.dto.ClientDTO;
import com.clients.vsproj.entities.Client;
import com.clients.vsproj.repository.ClientRepository;
import com.clients.vsproj.services.exceptions.DataBaseException;
import com.clients.vsproj.services.exceptions.ResourceNotFaundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository cr;

//buscar por ID
    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Optional<Client> result = cr.findById(id);
        Client client = result.orElseThrow(
                ()-> new ResourceNotFaundException("id has no correspoding client, recurso não encontrado")
        );
        ClientDTO cDTO = new ClientDTO(client);
        return cDTO;
    }
//buscar todos, paginado
    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable){
        Page<Client> clients = cr.findAll(pageable);
        return clients.map(c -> new ClientDTO(c));
    }
//Inserir novo cliente
    @Transactional
    public ClientDTO insert(ClientDTO dto){
        Client entity = new Client();
        copyDtoToEntity(dto, entity);
        entity = cr.save(entity);
        return new ClientDTO(entity);
    }
//Atualizar cliente
    @Transactional
    public ClientDTO update(Long id, ClientDTO dto){
        try {
            Client entity = cr.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = cr.save(entity);
            return  new ClientDTO(entity);
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFaundException("não foi possivel deletar, recurso não encontrado");
        }
    }

 //Deletar Cliente
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if(!cr.existsById(id)) {
            throw new ResourceNotFaundException("id não encontrado! " + id);
        }
        try{
            cr.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ResourceNotFaundException("não foi possivel deletar, recurso não encontrado");
        }catch (DataIntegrityViolationException e){
            throw new DataBaseException("Integridade violado, não é possivel deletar o Cliente de id: "+id);
        }
    }


//copiar DTO para entidade
    public void copyDtoToEntity(ClientDTO dto ,Client entity){
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}
