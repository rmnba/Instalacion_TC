import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sun.jna.WString;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.WinBase.PROCESS_INFORMATION;
import com.sun.jna.platform.win32.WinBase.STARTUPINFO;

public class Ventana extends JFrame implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	
	private JPanel panelArriba;
	
	private JPanel panelMedio;
	
	private JPanel panelAbajo;
	
	private JLabel etiqueta;
	
	private JLabel etiqueta2;
	
	private JLabel etiquetaDom;
	
	private JLabel etiquetaSin;
	
	private JTextField nombre;
	
	private JTextField ruta;
	
	private JButton ejecutar;
	
	private JButton ayudaNombre;
	
	public Ventana ()
	{
		super("Instalación TC");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		
		this.panelArriba = new JPanel();
		this.panelArriba.setLayout(new BoxLayout(this.panelArriba, BoxLayout.Y_AXIS));
		
		this.panelMedio = new JPanel();
		this.panelMedio.setLayout(new BoxLayout(this.panelMedio, BoxLayout.Y_AXIS));
		
		this.panelAbajo = new JPanel();
		this.panelAbajo.setLayout(new BorderLayout());
		
		String textoDom = "<html><body><b> Ejemplo con máquina ligada a un dominio:<b> <i> administrator@dominio.local <i> </body></html>";
		String textoSin = "<html><body><b> Ejemplo sin máquina ligada a un dominio: <b> <i> administrator <i> </body></html>";
		
		this.etiquetaDom = new JLabel (textoDom);
		this.etiquetaDom.setForeground(Color.BLUE);
		this.etiquetaSin = new JLabel (textoSin);
		this.etiquetaSin.setForeground(Color.MAGENTA);
		
		this.etiqueta2 = new JLabel ("Introduzca su nombre de usuario administrador: ");
		
		this.nombre = new JTextField();
		
		this.etiqueta = new JLabel ("Introduzca la ruta: ");
		
		this.ruta = new JTextField();
		
		this.ayudaNombre = new JButton ("Ayuda nombre usuario");
		this.ayudaNombre.addActionListener(this);
		
		this.panelArriba.add(this.etiqueta2);
		this.panelArriba.add(this.nombre);
		
		this.panelMedio.add(this.etiqueta);
		this.panelMedio.add(this.ruta);
		
		this.ejecutar = new JButton ("Ejecutar");
		this.ejecutar.addActionListener(this);

		this.panelAbajo.add(this.ayudaNombre, BorderLayout.WEST);
		this.panelAbajo.add(this.ejecutar, BorderLayout.EAST);
		
		this.panel.add(this.panelArriba, BorderLayout.NORTH);
		this.panel.add(this.panelMedio, BorderLayout.CENTER);
		this.panel.add(this.panelAbajo, BorderLayout.SOUTH);
		
		this.add(this.panel);
		this.setSize(500, 200);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand().equals("Ejecutar"))
		{
			String admin = this.nombre.getText();
			
			String rutaIns = this.ruta.getText();
			
			JPasswordField pass = new JPasswordField();
			
			JLabel label = new JLabel("Escriba la contraseña para " + admin + ":");
			
			try
			{
				
				/*String cmd[] = new String [3];
				cmd [0] = "cmd";
				cmd [1] = "/C";
				cmd [2] = "runas /user:" + admin + " " + rutaIns;
				

				Process p = Runtime.getRuntime().exec(cmd);
				//p.waitFor();
				
				InputStream is = p.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);*/
				
				//String line;

				JOptionPane.showConfirmDialog(null, new Object[]{label, pass}, "Password", JOptionPane.OK_CANCEL_OPTION);				
								
				PROCESS_INFORMATION processInformation = new PROCESS_INFORMATION();
			    STARTUPINFO startupInfo = new STARTUPINFO();
			    
			    boolean result = ProcesoUsuario.INSTANCE.CreateProcessWithLogonW
			       (new WString(admin),                         			// user
			        null,                                           		// domain, null if local
			        new WString(String.valueOf(pass.getPassword())),        // password
			        ProcesoUsuario.LOGON_WITH_PROFILE,                 		// dwLogonFlags
			        null,                                           		// lpApplicationName
			        new WString(rutaIns),   								// command line
			        ProcesoUsuario.CREATE_NEW_CONSOLE,                 		// dwCreationFlags
			        null,                                            		// lpEnvironment
			        null,							                   		// directory
			        startupInfo,
			        processInformation);
				
			    if (!result)
			    {
			    	int error = Kernel32.INSTANCE.GetLastError();
			    	JOptionPane.showMessageDialog(null, Kernel32Util.formatMessageFromLastErrorCode(error), "Error", JOptionPane.ERROR_MESSAGE);
			    }
				
			}
			catch (Exception e1)
			{
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else if (e.getActionCommand().equals("Ayuda nombre usuario"))
		{
			final JFrame frameAyuda = new JFrame ("Pantalla de ayuda");
			
			frameAyuda.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			frameAyuda.setSize(500, 150);
			
			JPanel panelAyuda = new JPanel ();
			
			JButton aceptar = new JButton ("Aceptar");
			
			aceptar.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) 
					{
						frameAyuda.dispose();
					}
				
				});
			
			panelAyuda.setLayout(new BorderLayout());
			
			panelAyuda.add(this.etiquetaDom, BorderLayout.NORTH);
			panelAyuda.add(this.etiquetaSin, BorderLayout.CENTER);
			panelAyuda.add(aceptar, BorderLayout.SOUTH);
			
			frameAyuda.add(panelAyuda);
			
			frameAyuda.setVisible(true);
		}
	}
	
	
}
