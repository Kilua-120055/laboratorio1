package gt.edu.umg.ingenieria.sistemas.laboratorio1.controller;

import gt.edu.umg.ingenieria.sistemas.laboratorio1.model.Client;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.service.ClientService;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.service.ReportService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Josué Barillas (jbarillas)
 */

@RestController
public class ClientController {

    @Autowired
    private ClientService servicioCliente;
    
    @GetMapping("/clientes/buscarTodos")
    public List<Client> todos() {
        
        return this.servicioCliente.findAll();
    }
    
    @GetMapping("/clientes/buscarPorNit")
    public Client por_nit(@RequestParam(name = "nit", required = true) String buscar) {
        
        return this.servicioCliente.findNit(buscar);
    }
    
   @GetMapping("/clientes/buscarPorNombreApellido")
    
   public List<Client> getByName(@RequestParam(name = "query", required = true) String query) {
        List<Client> mostresp= new ArrayList<Client>();
        List<Client> auxiliar= null;
        int var1=query.indexOf(" ");
        if(var1>0){
            auxiliar = this.servicioCliente.findAll();
            for (int i=0; i<auxiliar.size();i++){
                if(auxiliar.get(i).getFirstName().equals(query.substring(0,var1)) && auxiliar.get(i).getFirstName().equals(query.substring(var1+1))){
                    mostresp.add(auxiliar.get(i));
                }
            }
        }
        else{
            int aste=query.indexOf("*");
            if(aste>=0){
              if(aste==0){
                auxiliar = this.servicioCliente.findAll();
                  for (int i=0; i<auxiliar.size();i++){
                    if(auxiliar.get(i).getFirstName().equals(query.substring(var1+1))){
                    mostresp.add(auxiliar.get(i));
                    }
                  }
              }
        else{
            if(aste==(query.length()-1)){
                auxiliar = this.servicioCliente.findAll();
                 for (int i=0; i<auxiliar.size();i++){
                    if(auxiliar.get(i).getLastName().equals(query.substring(var1+1))){
                      mostresp.add(auxiliar.get(i));
                      }
                    }
                 }
        else{
            auxiliar = this.servicioCliente.findAll();
              String combinacion="";
                for (int i=0; i<auxiliar.size();i++){
                   combinacion = auxiliar.get(i).getFirstName()+auxiliar.get(i).getLastName();
                    if(combinacion.startsWith(query.substring(0,aste)) && combinacion.endsWith(query.substring(aste+1))){
                      mostresp.add(auxiliar.get(i));
                      }
                }
             }
            }
        }
        else{
            auxiliar = this.servicioCliente.findAll();
              String combinacion="";
              for (int i=0; i<auxiliar.size();i++){
                combinacion = auxiliar.get(i).getFirstName()+auxiliar.get(i).getLastName();
                    if(combinacion.equals(query)){
                      mostresp.add(auxiliar.get(i));
                  }
              }
          }
      }
     return mostresp;
    }
   
   @PostMapping("/clientes/crearCliente")
   
   public Client crearClientes(@RequestBody(required = true) Client varcliente) {
        
        Client cliente= new Client();
        if(varcliente.getNit().length()>10){
   
            cliente.setId(Long.parseLong("0"));
            cliente.setFirstName("");
            cliente.setLastName("");
            cliente.setAddress("");
            cliente.setPhone("");
            cliente.setNit("NIT Invalido");
        
        }
        
        else if(varcliente.getNit().isEmpty()==true){
        
            cliente.setNit("Lo sentimos. El sistema solo permite el registro de usuarios mayores de edad.");
            cliente.setId(Long.parseLong("0"));
            cliente.setFirstName("");
            cliente.setLastName("");
            cliente.setAddress("");
            cliente.setPhone("");
        
        }
        
        else{
        
            try {
            
                varcliente.setFirstName(varcliente.getFirstName().substring(0,1).toUpperCase()+varcliente.getFirstName().substring(1).toLowerCase());
                varcliente.setLastName(varcliente.getLastName().substring(0,1).toUpperCase()+varcliente.getLastName().substring(1).toLowerCase());
            
            } catch (Exception e) {
            
            }
            
            cliente = this.servicioCliente.setNew(varcliente);
           
            
        }
        return cliente;
    }

    
    @PutMapping("/clientes/editarCliente")
    
    public Client editarClientes(@RequestBody(required = true) Client varcliente) {
        
        Client cliente = new Client();
        
        if(varcliente.getNit().length()>10){
    
            cliente.setId(Long.parseLong("0"));
            cliente.setFirstName("");
            cliente.setLastName("");
            cliente.setAddress("");
            cliente.setPhone("");
            cliente.setNit("NIT Invalido");
            
        }
        
        else if(varcliente.getNit().isEmpty()==true){
        
            cliente.setNit("Lo sentimos. El sistema solo permite el registro de usuarios mayores de edad.");
            cliente.setId(Long.parseLong("0"));
            cliente.setFirstName("");
            cliente.setLastName("");
            cliente.setAddress("");
            cliente.setPhone("");
        
        }
        
        else{
        
            varcliente.setFirstName(varcliente.getFirstName().substring(0,1).toUpperCase()+varcliente.getFirstName().substring(1).toLowerCase());
            varcliente.setLastName(varcliente.getLastName().substring(0,1).toUpperCase()+varcliente.getLastName().substring(1).toLowerCase());
            varcliente= this.servicioCliente.setNew(varcliente);
        
        }
        
        return cliente;
    }
    
   @PutMapping("/clientes/editarCliente/{idcliente}/{numero}")
    
   public Client setUpdateNit(@PathVariable(required = true) long idcliente,@PathVariable(required = true) String numero) {
        
        Client clie= new Client();
        clie = this.servicioCliente.findId(idcliente);
        if(clie.getNit().length()>10){
   
            clie.setId(Long.parseLong("0"));
            clie.setFirstName("");
            clie.setLastName("");
            clie.setAddress("");
            clie.setPhone("");
            clie.setNit("NIT Invalido");
        
        }
        
        else{
        
            clie.setNit(numero);
            this.servicioCliente.setUpdate(clie);
        
        }
        
        return clie;
    }
    
    @PutMapping("/clientes/editarCliente/{idcliente}/{nombre}/{apellido}")
    
    public Client setUpdateNombre(@PathVariable(required = true) long idcliente,@PathVariable(required = true) String nombre,@PathVariable(required = true) String apellido) {
        
        Client cliente= new Client(); 
        cliente = this.servicioCliente.findId(idcliente);
        
        try{
            cliente.setFirstName(nombre.substring(0,1).toUpperCase()+nombre.substring(1).toLowerCase());
            cliente.setLastName(apellido.substring(0,1).toUpperCase()+apellido.substring(1).toLowerCase());
        }
  
        catch(Exception a){
       
        }
        
        return this.servicioCliente.setUpdate(cliente);
    }
 
    @GetMapping("/clientes/generarReporteClientes")
    
    public String reporte() {
        
        String htmlCadena = "Reporte de Clientes <br><br>";
        
        String carpeta = "/var/www/";
        File f = new File(carpeta);
        File[] ficheros = f.listFiles();
        int cont=1;
        for (int x=0;x<ficheros.length;x++){
            cont++;            
        }
        
        File reportes = new File("/var/www/Clientes_"+String.valueOf(cont)+".html");
        List<Client> mostresp = this.servicioCliente.findAll();
        
        try{
            BufferedWriter buffer = new BufferedWriter(new FileWriter(reportes));
        for(int i=0; i<mostresp.size();i++){
            htmlCadena= htmlCadena+"  \t" +
            mostresp.get(i).getId()+"  \t" +
            mostresp.get(i).getFirstName()+"  \t" +
            mostresp.get(i).getLastName()+"  \t" +
            mostresp.get(i).getNit()+"  \t" +
            mostresp.get(i).getPhone()+"  \t" +
            mostresp.get(i).getAddress()+"  \t" +
            "<br>" ;
        }
        
        buffer.write(htmlCadena);
        buffer.close();
        }
        catch(Exception e){
         System.out.println(e);
        }
        
        return "El reporte  var/www/Clientes_"+String.valueOf(cont)+".html ha sido generado.";
    }
    
    
}
