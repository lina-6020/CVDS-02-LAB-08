package edu.eci.cvds.samples.services.client;

import com.google.common.base.Strings;

import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;

public class Main {
	public static void main(Strings []args)throws ExcepcionServiciosAlquiler{
		 System.out.println(ServiciosAlquilerFactory.getInstance().getServiciosAlquiler().consultarCliente(0));
	     System.out.println(ServiciosAlquilerFactory.getInstance().getServiciosAlquiler().consultarCostoAlquiler(99,2));
	     System.exit(0);
	}
}
