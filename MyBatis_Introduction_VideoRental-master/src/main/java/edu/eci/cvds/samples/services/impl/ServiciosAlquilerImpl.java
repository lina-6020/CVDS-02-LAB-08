package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ServiciosAlquilerImpl implements ServiciosAlquiler {

   @Inject
   private ItemDAO itemDAO;
   @Inject
   private ClienteDAO clienteDAO;
   @Inject
   private TipoItemDAO tipoItemDAO;

   @Override
   public long valorMultaRetrasoxDia(int itemId)throws ExcepcionServiciosAlquiler {
       try {
    	   return itemDAO.consultarItem(itemId).getTarifaxDia();
       }catch (PersistenceException e) {
    	   throw new ExcepcionServiciosAlquiler("El item no esta registrado");
    	   
       }
   }

   @Override
   public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
       try {
    	 if (clienteDAO.consultarCliente(docu)==null) {
    		 throw new ExcepcionServiciosAlquiler("El usuario no esta registrado"); 
    	 }else {
    		 return clienteDAO.consultarCliente(docu);
    	 }   
       }catch (PersistenceException e) {
    	  throw new ExcepcionServiciosAlquiler("El cliente no esta registrado");
	}
   }

   @Override
   public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
	   Cliente cliente=consultarCliente(idcliente);
	   if (cliente==null) {
		   throw new ExcepcionServiciosAlquiler("El cliente no se encuentra en la base de datos");
	   }else {
		   return cliente.getRentados();
	   }
   }

   @Override
   public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
       try {
    	   return clienteDAO.consultarClientes();
       }catch (PersistenceException e) {
		throw new ExcepcionServiciosAlquiler("No se pueden consultar todos los clientes");
	}
   }

   @Override
   public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
       try {
           return itemDAO.consultarItem(id);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar el item "+id,ex);
       }
   }

   @Override
   public List<Item> consultarItemsDisponibles() throws ExcepcionServiciosAlquiler{
	   try {
           return itemDAO.consultarItems();
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar el item ",ex);
       }
   }

   @Override
   public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
       List<Cliente> clientes=consultarClientes();
       for (int i=0;i<clientes.size();i++) {
    	   ArrayList<ItemRentado>rentados=clientes.get(i).getRentados();
    	   for(int j=0;i<rentados.size();i++) {
    		   if(rentados.get(i).getItem().getId()==iditem) {
    			   LocalDate fechaFinRenta=rentados.get(i).getFechafinrenta().toLocalDate();
    			   LocalDate fechaInicioRenta=rentados.get(i).getFechainiciorenta().toLocalDate();
    			   long demora=ChronoUnit.DAYS.between(fechaFinRenta, fechaInicioRenta);
    			   if(demora<0) {
    				   return 0;
    			   }else {
    				   return demora*valorMultaRetrasoxDia(rentados.get(i).getItem().getId());
    			   }
    		   }
    	   }
       }
       throw new ExcepcionServiciosAlquiler("El item"+iditem+"Se encuentra Disponible");
   }

   @Override
   public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
       try {
    	   return tipoItemDAO.consultarTipoItems(id);
       }catch (PersistenceException e) {
    	   throw new ExcepcionServiciosAlquiler("Error al consultar el tipo de item"+id,e);
       }
   }

   @Override
   public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
       	try {
       		return tipoItemDAO.consultarTipoItems();
       	}catch (PersistenceException e) {
			throw new ExcepcionServiciosAlquiler("Error al consultar tipos de item",e);
		}
   }

   @Override
   public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
       throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
       throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
       throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
       throw new UnsupportedOperationException("Not supported yet.");
   }
   @Override
   public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public void registrarTipoItem(TipoItem tipoItem) throws ExcepcionServiciosAlquiler {
	   throw new UnsupportedOperationException("Not supported yet");
	
}
}