package procesos;

import com.sun.jna.WString;
import com.sun.jna.platform.win32.WinBase.PROCESS_INFORMATION;
import com.sun.jna.platform.win32.WinBase.STARTUPINFO;

public class LanzamientoInstalacion 
{
	public static boolean resultadoEjecucion (String user, String password, String ruta, PROCESS_INFORMATION processInformation)
	{
		boolean resul = false;
		
	    STARTUPINFO startupInfo = new STARTUPINFO();
	    
	    resul = ProcesoUsuario.INSTANCE.CreateProcessWithLogonW
			       (new WString(user),                         			// user
			        null,                                           	// domain, null if local
			        new WString(String.valueOf(password)),        		// password
			        ProcesoUsuario.LOGON_WITH_PROFILE,                 	// dwLogonFlags
			        null,                                           	// lpApplicationName
			        new WString(ruta),   								// command line
			        ProcesoUsuario.CREATE_NEW_CONSOLE,                 	// dwCreationFlags
			        null,                                            	// lpEnvironment
			        null,							                   	// directory
			        startupInfo,
			        processInformation);
		
		return resul;
	}
	
	
}
