package mvc.control;

import java.io.File;

import mvc.modelo.Modelo;
import mvc.vista.Ventana;

public class Controlador 
{
	private Modelo modelo;
	
	public Controlador (Ventana v)
	{
		modelo = new Modelo(v);
	}
	
	public void run (String admin, String password, String rutaIns)
	{
		modelo.run(admin, password, rutaIns);
	}
	
	public void copiado (File src, File dst)
	{
		// Desconozco cuanto tiempo podría llevar el copiado de archivos, por eso no sé
		// durante cuánto tiempo habría que dormir el proceso principal
		
		modelo.copiado(src, dst);
		
		// Thread.sleep(300000);
	}
	
	public void runAct (String admin, String password, String rutaIns)
	{
		modelo.runAct(admin, password, rutaIns);
	}

}
