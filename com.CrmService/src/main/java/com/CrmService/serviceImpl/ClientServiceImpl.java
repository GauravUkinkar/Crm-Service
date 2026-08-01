package com.CrmService.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.CrmService.dto.ClientDTO;
import com.CrmService.dto.Message;
import com.CrmService.mapper.ClientMapper;
import com.CrmService.model.Client;
import com.CrmService.model.Project;
import com.CrmService.repository.ClientRepository;
import com.CrmService.repository.ProjectsRepository;
import com.CrmService.service.ClientService;
import com.CrmService.util.Constants;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ClientServiceImpl implements ClientService {

	public final ClientMapper mapper;
	public final ClientRepository repository;
	public final ProjectsRepository projectRepository;

	public ClientServiceImpl(ClientMapper mapper, ClientRepository repository, ProjectsRepository projectRepository) {
		super();
		this.mapper = mapper;
		this.repository = repository;
		this.projectRepository = projectRepository;
	}

	@Override
	public Message<ClientDTO> addClient(ClientDTO client) {
		Message<ClientDTO> message = new Message<>();
		log.info("Incoming request body: {}", client);

		try {
			Client cli = repository.getByName(client.getName());
			if (cli != null && cli.getName().equals(client.getName())) {
				message.setResponseMessage(Constants.ALREADY_PRESENT);
				message.setStatus(HttpStatus.CONFLICT);
				log.error("Error facing in AddClient(): {}", message.getResponseMessage());
				return message;
			}
			Client cl = mapper.toClientEntity(client);
			repository.save(cl);
			message.setResponseMessage(Constants.CLIENT_ADD);
			message.setStatus(HttpStatus.CREATED);
			message.setData(mapper.toClientDTO(cl));
			log.info("Client added successfully with request: {}", client);
			return message;
		} catch (Exception e) {
			log.error("In ClientService AddClient() exception: ", e);
			message.setResponseMessage("Failed to add client: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<ClientDTO> updateClient(ClientDTO client) {
		Message<ClientDTO> message = new Message<>();
		log.info("Incoming request body: {}", client);
		try {
			Optional<Client> optionalClient = repository.findById(client.getId());
			if (!optionalClient.isPresent()) {
				message.setResponseMessage(Constants.RECORD_NOT_FOUND);
				message.setStatus(HttpStatus.NOT_FOUND);
				log.error("Error in UpdateClient(): {}", Constants.RECORD_NOT_FOUND);
				return message;
			}
			Client cli = repository.getByName(client.getName());
			if (cli != null && cli.getName().equals(client.getName())) {
				message.setResponseMessage(Constants.ALREADY_PRESENT);
				message.setStatus(HttpStatus.CONFLICT);
				log.error("Error facing in AddClient(): {}", message.getResponseMessage());
				return message;
			}
			Client cl = mapper.toClientEntity(client);
			repository.save(cl);
			List<Project> projectList = projectRepository.findAllByClientName(optionalClient.get().getName());
			for (Project pro : projectList) {
			    pro.setClientName(client.getName());
			}		
			projectRepository.saveAll(projectList);
			message.setData(mapper.toClientDTO(cl));
			message.setResponseMessage(Constants.UPDATE_SUCCESS);
			message.setStatus(HttpStatus.OK);
			log.info("Client updated successfully with request: {}", client);
			return message;
		} catch (Exception e) {
			log.error("In ClientServiceImpl UpdateClient() exception: ", e);
			message.setResponseMessage("Failed to update client: " + e.getMessage());
			return message;
		}
	}

	@Override
	public Message<ClientDTO> getClient(String name) {
		Message<ClientDTO> message = new Message<>();
		log.info("Incoming request body: {}", name);
		Client cli = repository.getByName(name);
		try {
			if (cli.getName() != null) {
				message.setData(mapper.toClientDTO(cli));
				message.setResponseMessage(Constants.RECORD_FOUND);
				message.setStatus(HttpStatus.OK);
				return message;
			} else {
				message.setResponseMessage(Constants.RECORD_NOT_FOUND);
				message.setStatus(HttpStatus.BAD_REQUEST);
				log.error("Error facing in UpdateClirent()" + message.getResponseMessage());
				return message;
			}
		} catch (Exception e) {
			log.error("In ClientServiceImpl UpdateClient() exception: {}", e.getMessage());
			message.setResponseMessage("Failed to add Item: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}

	}

	@Override
	public Message<ClientDTO> deleteClient(int id) {
		Message<ClientDTO> message = new Message<>();
		log.info("Incoming request Id: {}", id);
		Optional<Client> optionalClient = repository.findById(id);
		if (optionalClient.isPresent()) {
			Client client = optionalClient.get();
			String clientName = client.getName();
			projectRepository.deleteAllByClientName(clientName);
			repository.delete(client);
			message.setResponseMessage("Client deleted successfully");
			message.setStatus(HttpStatus.NOT_FOUND);
			log.info("Client with Id {} deleted successfully", id);
			return message;
		} else {
			message.setResponseMessage("Client not found with Id: " + id);
			message.setStatus(HttpStatus.NOT_FOUND);
			log.warn("Client not found with Id: {}", id);
			return message;

		}

	}

	@Override
	public List<ClientDTO> getAllClients() {
		return repository.findAll().stream().map(mapper::toClientDTO).collect(Collectors.toList());

	}

	@Override
	public Message<ClientDTO> getClientById(int id) {
		Message<ClientDTO> message = new Message<>();
		log.info("Incoming request body: {}", id);
		try {
			Optional<Client> optionalClient = repository.findById(id);
			if (!optionalClient.isPresent()) {
				message.setResponseMessage(Constants.RECORD_NOT_FOUND);
				message.setStatus(HttpStatus.NOT_FOUND);
				log.error("Error in UpdateClient(): {}", Constants.RECORD_NOT_FOUND);
				return message;
			}

			Client cl = optionalClient.get();
			message.setData(mapper.toClientDTO(cl));
			message.setResponseMessage(Constants.RECORD_FOUND);
			message.setStatus(HttpStatus.OK);
			return message;
		} catch (Exception e) {
			log.error("In ClientServiceImpl UpdateClient() exception: ", e);
			message.setResponseMessage("Failed to update client: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

}
