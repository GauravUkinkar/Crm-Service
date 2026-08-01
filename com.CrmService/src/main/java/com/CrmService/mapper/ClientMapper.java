package com.CrmService.mapper;

import com.CrmService.dto.ClientDTO;
import com.CrmService.model.Client;

public interface ClientMapper {
	public ClientDTO toClientDTO(Client client);
	public Client toClientEntity(ClientDTO dto);
}
