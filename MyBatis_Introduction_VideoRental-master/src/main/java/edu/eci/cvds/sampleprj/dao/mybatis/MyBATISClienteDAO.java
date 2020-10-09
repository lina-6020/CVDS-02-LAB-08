package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;

public class MyBATISClienteDAO {
	@Inject
	  private ClienteMapper clienteMapper;    

	  @Override
	  public void save(Cliente cliente) throws PersistenceException{
	  try{
	      clienteMapper.insertarCliente(cliente);
	  }
	  catch(org.apache.ibatis.exceptions.PersistenceException e){
	      throw new PersistenceException("Error al registrar el cliente "+cliente.toString(),e);
	  }        

	  }

	  @Override
	  public Cliente consultarCliente(long id) throws PersistenceException {OCE
	  try{
	      return clienteMapper.consultarCliente(id);
	  }
	  catch(org.apache.ibatis.exceptions.PersistenceException e){
	      throw new PersistenceException("Error al consultar el cliente "+id,e);
	  }


	  }
	  @Override
	    public List<Cliente> consultarClientes() throws  PersistenceException{
	        try{
	            return clienteMapper.consultarCliente();
	        }
	        catch(org.apache.ibatis.exceptions.PersistenceException e) {
	            throw new PersistenceException("Error al consultar los clientes ", e);
	        }
	    }
	

}
