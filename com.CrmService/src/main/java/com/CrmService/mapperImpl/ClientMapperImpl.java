package com.CrmService.mapperImpl;

import org.springframework.stereotype.Component;

import com.CrmService.dto.ClientDTO;
import com.CrmService.mapper.ClientMapper;
import com.CrmService.model.Client;


@Component
public class ClientMapperImpl implements ClientMapper {

	@Override
	public ClientDTO toClientDTO(Client client) {
		 ClientDTO dto = new ClientDTO();
	        dto.setId(client.getId());
	        dto.setName(client.getName());
	        dto.setType(client.getType());
	        dto.setCategory(client.getCategory());
	        return dto;
	}

	@Override
	public Client toClientEntity(ClientDTO dto) {
		Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setType(dto.getType());
        client.setCategory(dto.getCategory());
        return client;
	}

}
