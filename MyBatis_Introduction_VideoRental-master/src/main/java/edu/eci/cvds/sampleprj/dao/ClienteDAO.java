package edu.eci.cvds.sampleprj.dao;

import java.sql.Date;
import java.util.List;

import edu.eci.cvds.samples.entities.Cliente;

public interface ClienteDAO {
	public void save(Cliente cliente) throws PersistenceException;

	public Cliente consultarCliente(long id) throws PersistenceException;
	
	public List<Cliente> consultarClientes() throws PersistenceException;
	
	public void agregarItemRentadoACliente(long idCliente,int idItem,Date fechaInicio,Date fechaFin)throws PersistenceException;
	
	public void vetarCliente (long idCliente, int estado)throws PersistenceException;

}
