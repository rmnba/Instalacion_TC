package procesos;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Advapi32;
import com.sun.jna.platform.win32.WinBase.PROCESS_INFORMATION;
import com.sun.jna.platform.win32.WinBase.STARTUPINFO;

public interface ProcesoUsuario extends Advapi32
{
	ProcesoUsuario INSTANCE = (ProcesoUsuario) Native.loadLibrary("AdvApi32", ProcesoUsuario.class);
	
	boolean CreateProcessWithLogonW
    (WString lpUsername,
     WString lpDomain,
     WString lpPassword,
     int dwLogonFlags,
     WString lpApplicationName,
     WString lpCommandLine,
     int dwCreationFlags,
     Pointer lpEnvironment,
     WString lpCurrentDirectory,
     STARTUPINFO  lpStartupInfo,
     PROCESS_INFORMATION lpProcessInfo);

	public static final int LOGON_WITH_PROFILE          = 0x00000001;
	public static final int LOGON_NETCREDENTIALS_ONLY   = 0x00000002;


	public static int CREATE_NO_WINDOW            = 0x08000000;
	public static int CREATE_UNICODE_ENVIRONMENT  = 0x00000400;
	public static int CREATE_NEW_CONSOLE          = 0x00000010;
	public static int DETACHED_PROCESS            = 0x00000008;
}
