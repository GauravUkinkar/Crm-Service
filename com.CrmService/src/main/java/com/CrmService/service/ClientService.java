package com.CrmService.service;

import java.util.List;

import com.CrmService.dto.ClientDTO;
import com.CrmService.dto.Message;

public interface ClientService {
public Message<ClientDTO>addClient(ClientDTO client);
public Message<ClientDTO>updateClient(ClientDTO client);
public Message<ClientDTO>getClient(String name);
public Message<ClientDTO>deleteClient(int id);
public List<ClientDTO> getAllClients();
public Message<ClientDTO>getClientById(int id);


}
