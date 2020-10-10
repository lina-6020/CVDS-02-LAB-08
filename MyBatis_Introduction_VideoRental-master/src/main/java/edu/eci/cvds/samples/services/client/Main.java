package edu.eci.cvds.samples.services.client;

import com.google.common.base.Strings;

import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;

public class Main {
	public static void main(Strings []args)throws ExcepcionServiciosAlquiler{
		 System.out.println("------CONSULTAMAIN------");
		 System.out.println(ServiciosAlquilerFactory.getInstance().getServiciosAlquiler().consultarCliente(6584562));
	     System.out.println(ServiciosAlquilerFactory.getInstance().getServiciosAlquiler().consultarCostoAlquiler(199,4));
	     System.exit(0);
	}
}
