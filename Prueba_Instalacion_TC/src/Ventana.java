import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	
	private JPanel panelInterno;
	
	private JLabel etiqueta;
	
	private JLabel etiqueta2;
	
	private JLabel etiquetaDom;
	
	private JLabel etiquetaSin;
	
	private JTextField nombre;
	
	private JTextField ruta;
	
	private JButton instalar;
	
	public Ventana ()
	{
		super("Instalación TC");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		
		this.panelInterno = new JPanel();
		this.panelInterno.setLayout(new BoxLayout(this.panelInterno, BoxLayout.Y_AXIS));
		
		String textoDom = "<html><body><b> Ejemplo con máquina ligada a un dominio:<b> <i> administrator@dominio.local <i> </body></html>";
		String textoSin = "<html><body><b> Ejemplo sin máquina ligada a un dominio: <b> <i> administrator <i> </body></html>";
		
		this.etiquetaDom = new JLabel (textoDom);
		this.etiquetaDom.setForeground(Color.BLUE);
		this.etiquetaSin = new JLabel (textoSin);
		this.etiquetaSin.setForeground(Color.MAGENTA);
		
		this.etiqueta2 = new JLabel ("Introduzca su nombre de usuario administrador: ");
		this.etiqueta2.setForeground(Color.RED);
		
		this.nombre = new JTextField();
		
		this.etiqueta = new JLabel ("Introduzca la ruta: ");
		
		this.ruta = new JTextField();
		
		this.panelInterno.add(this.etiqueta2);
		this.panelInterno.add(this.etiquetaDom);
		this.panelInterno.add(this.etiquetaSin);
		this.panelInterno.add(this.nombre);
		this.panelInterno.add(this.etiqueta);
		this.panelInterno.add(this.ruta);
		
		this.instalar = new JButton ("Instalar");
		this.instalar.addActionListener(this);
		
		this.panel.add(this.panelInterno, BorderLayout.NORTH);
		this.panel.add(this.instalar, BorderLayout.SOUTH);
		
		this.add(this.panel);
		this.setSize(500, 250);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand().equals("Instalar"))
		{
			String admin = this.nombre.getText();
			
			String rutaIns = this.ruta.getText();
			
			JPasswordField pass = new JPasswordField();
			
			JLabel label = new JLabel("Escriba la contraseña para " + admin + ":");
			
			try
			{
				
				String cmd[] = new String [3];
				cmd [0] = "cmd";
				cmd [1] = "/C";
				cmd [2] = "runas /user:" + admin + " " + rutaIns;
				
				//cmd [3] = String.valueOf(pass.getPassword());

				/*Process p = Runtime.getRuntime().exec(cmd);
				//p.waitFor();
				
				InputStream is = p.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);*/
				
				//String line;

				JOptionPane.showConfirmDialog(null, new Object[]{label, pass}, "Password", JOptionPane.OK_CANCEL_OPTION);
				
				/*while ((line = br.readLine()) != null) 
				{
					if (line.contains("Escriba la contra"))
						line = new String ("Escriba la contraseña para " + admin + ":");
					//JOptionPane.showMessageDialog(null, line, "Alerta", JOptionPane.INFORMATION_MESSAGE);
				}*/
								
				PROCESS_INFORMATION processInformation = new PROCESS_INFORMATION();
			    STARTUPINFO startupInfo = new STARTUPINFO();
			    
			    boolean result = MoreAdvApi32.INSTANCE.CreateProcessWithLogonW
			       (new WString(admin),                         			// user
			        null,                                           		// domain , null if local
			        new WString(String.valueOf(pass.getPassword())),        // password
			        MoreAdvApi32.LOGON_WITH_PROFILE,                 		// dwLogonFlags
			        null,                                           		// lpApplicationName
			        new WString(rutaIns),   								// command line
			        MoreAdvApi32.CREATE_NEW_CONSOLE,                 		// dwCreationFlags
			        null,                                            		// lpEnvironment
			        null,							                   		// directory
			        startupInfo,
			        processInformation);
				
			    if (!result)
			    {
			    	int error = Kernel32.INSTANCE.GetLastError();
			    	JOptionPane.showMessageDialog(null, Kernel32Util.formatMessageFromLastErrorCode(error), "Error", JOptionPane.ERROR_MESSAGE);
			    }
				//br.close();
				
			}
			catch (Exception e1)
			{
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
}
