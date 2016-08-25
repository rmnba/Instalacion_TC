package mvc.modelo;

import java.io.File;
import java.io.IOException;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinBase.PROCESS_INFORMATION;
import com.sun.jna.win32.W32APIOptions;

import copiado.CopiarArchivo;
import mvc.vista.Ventana;
import procesos.LanzamientoInstalacion;

public class Modelo 
{

	private Ventana ventana;
	
	public Modelo(Ventana v)
	{
		ventana = v;
	}

	/**
	 * Método que lanza la ejecución de la aplicación, pasándole el nombre de usuario administrador
	 * y la ruta donde se encuentra el archivo a ejecutar. Ésta, a su vez, comprueba llamando al método
	 * "while_install" que continúa ejecutando el proceso, para no continuar mientras exista el proceso.
	 * @param admin
	 * @param password
	 * @param rutaIns
	 */
	public void run (String admin, String password, String rutaIns)
	{
		PROCESS_INFORMATION processInformation = new PROCESS_INFORMATION();
		
		boolean result = LanzamientoInstalacion.resultadoEjecucion(admin, password, rutaIns, processInformation);
		
		// La forma de comprobar que el proceso continúa ejecutando es con su pid.
		int pid = processInformation.dwProcessId.intValue();
			
		if (!result)
		{
		    int error = Kernel32.INSTANCE.GetLastError();
		    ventana.updateError(error);
		}
		else
		{
			boolean jambo = true;
			
			while (jambo)
				jambo = this.while_install(pid);
			
			ventana.update();
		}
	}
	
	/**
	 * Método que permite esperar mientras la ejecución de la aplicación 
	 * está en proceso comprobando su pid, previamente al copiado de archivos.
	 * @param pid
	 * @return
	 */
	private boolean while_install (int pid)
	{
		
		Kernel32 kernel32 = (Kernel32) Native.loadLibrary(Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
	    Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();          

	    WinNT.HANDLE snapshot = kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));
	    try  
	    {
	    	int i = 0;
	    	
	    	int size = processEntry.dwSize.intValue();
	    	
	    	while (kernel32.Process32Next(snapshot, processEntry) && i < size) 
	    	{             
	    		if (processEntry.th32ProcessID.intValue() == pid)
	    			return true;
	    		i++;
	        }
	    	
	    }
	    finally 
	    {
	        kernel32.CloseHandle(snapshot);
	    }
	    return false;
	}
	
	/**
	 * Copia el contenido del directorio fuente "src" en el directorio destino "dst"
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	public void copiado (File src, File dst)
	{
		CopiarArchivo cop = CopiarArchivo.getInstance();
		
		try
		{
			cop.copyDirectory(src, dst);				
			ventana.update2();		
		}
		catch (IOException e)
		{
			ventana.updateErrorCopy(e.getMessage());
		}		
	}
	
	/**
	 * Lanza la ejecución del proceso de actualización
	 * @param admin
	 * @param password
	 * @param rutaIns
	 */
	public void runAct (String admin, String password, String rutaIns)
	{
		PROCESS_INFORMATION processInformation = new PROCESS_INFORMATION();
		
		boolean result = LanzamientoInstalacion.resultadoEjecucion(admin, password, rutaIns, processInformation);
		
		int pid = processInformation.dwProcessId.intValue();
		
		int error = 0;
		
		if (!result)
		{
		    error = Kernel32.INSTANCE.GetLastError();
		    ventana.updateError(error);
		}
		else
		{
			boolean jambo = true;
			
			while (jambo)
				jambo = this.while_install(pid);
			
		}
		
		if (error == 0)
			ventana.resultadoInstalacion(result);
	}
}
