package gt.edu.umg.ingenieria.sistemas.laboratorio1.service;

import gt.edu.umg.ingenieria.sistemas.laboratorio1.dao.ClientRepository;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.model.Client;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Josué Barillas (jbarillas)
 */

@Service
public class ClientService {
@Autowired
    private ClientRepository clienteRepositorio;    
  
     public List<Client> findAll() {
        return (List<Client>) this.clienteRepositorio.findAll();
    }
    
    public Client findNit(String criterio) {
        return this.clienteRepositorio.findClientByNit(criterio);
    }
    
    public Client findId(Long id) {
        return this.clienteRepositorio.findClientById(id);
    }
   
    public Client setNew(Client cliente) {               
        return this.clienteRepositorio.save(cliente);
    }
    
    public Client setUpdate(Client cliente) {
        return this.clienteRepositorio.save(cliente);        
    }    
    
    
}
