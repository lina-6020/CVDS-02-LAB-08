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

import org.mybatis.guice.transactional.Transactional;

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
   @Transactional
   public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
       LocalDate actual=date.toLocalDate();
       LocalDate entrega=actual.plusDays(numdias);
       if(numdias<1) {
    	   throw new ExcepcionServiciosAlquiler("El numero de dias del alquiler debe ser mayor a 1");
       }else if(consultarCliente(docu)==null) {
    	   throw new ExcepcionServiciosAlquiler("El cliente no se encuentra en la base de datos");
       }else if(consultarCliente(item.getId())==null) {
    	   throw new ExcepcionServiciosAlquiler("No existe el item buscado");
       }
       for(ItemRentado itemRentado:consultarCliente(docu).getRentados()) {
    	   if(itemRentado.getItem().getId()==item.getId()) {
    		   throw new ExcepcionServiciosAlquiler("El item ya se encuentra alquilado"+item.getId());
    	   }
       }try {
    	   clienteDAO.agregarItemRentadoACliente(docu, item.getId(),date,java.sql.Date.valueOf(entrega));
       }catch (PersistenceException e) {
    	   throw new ExcepcionServiciosAlquiler("Error al agregar el item"+item+"a los items rentados del cliente"+docu,e);
	}
   }
   @Override
   @Transactional
   public void registrarCliente(Cliente cliente) throws ExcepcionServiciosAlquiler {
       try {
    	   clienteDAO.save(cliente);
       }catch (PersistenceException e) {
    	   throw new ExcepcionServiciosAlquiler("Error al registrar el cliente"+cliente,e);
	}
   }

   @Override
   public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
      Item item=consultarItem(iditem);
      if(item==null || numdias<0) {
    	  throw new ExcepcionServiciosAlquiler("Los datos ingresados son erroneos");
      }else {
    	  return item.getTarifaxDia()*numdias;
      }
   }

   @Override
   @Transactional
   public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
       try {
    	   itemDAO.actualizarTarifaItem(id, tarifa);
       }catch (PersistenceException e) {
    	   throw new ExcepcionServiciosAlquiler("Error al cambiar la tarifa del item"+id,e);
	}
   }
   @Override
   @Transactional
   public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
       try {
    	   itemDAO.save(i);
       }catch (PersistenceException e) {
    	   throw new ExcepcionServiciosAlquiler("No se puede registrar este item"+i,e);
	}
   }

   @Override
   @Transactional
   public void vetarCliente(long docu, Boolean estado) throws ExcepcionServiciosAlquiler {
       try {
    	   clienteDAO.vetarCliente(docu, estado);
       }catch (PersistenceException e) {
    	   throw new ExcepcionServiciosAlquiler("Error al vetar al cliente"+docu,e);
	}
   }

   @Override
   public void registrarTipoItem(TipoItem tipoItem) throws ExcepcionServiciosAlquiler {
	   try {
		   tipoItemDAO.insertarTipoItem(tipoItem);
	   }catch (PersistenceException e) {
		   throw new ExcepcionServiciosAlquiler("No se puede registrar el tipo de item"+tipoItem);
	   }
	
   }
}