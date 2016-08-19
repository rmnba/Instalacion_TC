package ventana;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinBase.PROCESS_INFORMATION;
import com.sun.jna.win32.W32APIOptions;

import copiado.CopiarArchivo;
import procesos.LanzamientoInstalacion;

public class Ventana extends JFrame implements Serializable, ActionListener, Vista
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
	
	private JLabel lblRutaIni;
	
	private JLabel lblRutaFin;
	
	private JTextField txtRutaIni;
	
	private JTextField txtRutaFin;
	
	private JTextField nombre;
	
	private JTextField ruta;
	
	private JButton ejecutar;
	
	private JButton ayudaNombre;
	
	private JButton ejeAct;
	
	private JButton copiado;
	
	public Ventana ()
	{		
		super();
		
		JLabel titulo = new JLabel ("Instalación TC");
		titulo.setHorizontalAlignment(JLabel.CENTER);
		
		this.setTitle(titulo.getText());
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
		
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

		this.panelAbajo.add(this.ayudaNombre, BorderLayout.NORTH);
		this.panelAbajo.add(this.ejecutar, BorderLayout.SOUTH);
		
		this.panel.add(this.panelArriba, BorderLayout.NORTH);
		this.panel.add(this.panelMedio, BorderLayout.CENTER);
		this.panel.add(this.panelAbajo, BorderLayout.SOUTH);
		
		this.copiado = new JButton ("Copiado archivos");
		this.copiado.addActionListener(this);
		
		this.ejeAct = new JButton ("Ejecutar Actualización");
		this.ejeAct.addActionListener(this);
		
		this.lblRutaIni = new JLabel ("Ruta archivos de actualización:");
		this.lblRutaIni.setBorder(LineBorder.createGrayLineBorder());
		
		this.lblRutaFin = new JLabel ("Ruta de instalación de la actualización:");
		this.lblRutaFin.setBorder(LineBorder.createGrayLineBorder());
		
		this.txtRutaIni = new JTextField ();
		
		this.txtRutaFin = new JTextField ();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("Ejecutar"))
		{			
			boolean correcto = this.run(this.nombre.getText(), this.ruta.getText());
				
			if (correcto)
				this.update();
			
		}
		else if (e.getActionCommand().equals("Ayuda nombre usuario"))
		{
			final JDialog dialogAyuda = new JDialog (this, "Pantalla de ayuda - Nombre de usuario");
			
			dialogAyuda.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			dialogAyuda.setSize(500, 125);
			
			JPanel panelAyuda = new JPanel ();
			
			JButton aceptar = new JButton ("Aceptar");
			
			aceptar.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e) 
				{
					dialogAyuda.dispose();
				}
			
			});
			
			panelAyuda.setLayout(new BorderLayout());
			
			panelAyuda.add(this.etiquetaDom, BorderLayout.NORTH);
			panelAyuda.add(this.etiquetaSin, BorderLayout.CENTER);
			panelAyuda.add(aceptar, BorderLayout.SOUTH);
			
			dialogAyuda.add(panelAyuda);
			dialogAyuda.setLocationRelativeTo(this);
			dialogAyuda.setVisible(true);
		}
		else if (e.getActionCommand().equals("Copiado archivos"))
		{	
			try 
			{
				this.copiado(new File(this.txtRutaIni.getText()), new File(this.txtRutaFin.getText()));
				
				//Thread.sleep(300000);
				
				this.update2();
			} catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}/*catch (InterruptedException e2) 
			{
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}*/
		}
		else
		{
			boolean correcto = this.run(this.nombre.getText(), this.txtRutaIni.getText());
			
			if (correcto)
			{
				this.setVisible(false);
				JOptionPane.showMessageDialog(null, "Instalación correcta", "Correcto", JOptionPane.INFORMATION_MESSAGE);
				
				System.exit(0);
			}
			
		}
	}

	private boolean run(String admin, String rutaIns) 
	{
			
		JPasswordField pass = new JPasswordField();
			
		JLabel label = new JLabel("Escriba la contraseña para " + admin + ":");
		
		JOptionPane.showConfirmDialog(null, new Object[]{label, pass}, "Password", JOptionPane.OK_CANCEL_OPTION);
		
		PROCESS_INFORMATION processInformation = new PROCESS_INFORMATION();
			
		boolean result = LanzamientoInstalacion.resultadoEjecucion(admin, String.valueOf(pass.getPassword()), rutaIns, processInformation);
		
		int pid = processInformation.dwProcessId.intValue();
			
		if (!result)
		{
		    int error = Kernel32.INSTANCE.GetLastError();
		    JOptionPane.showMessageDialog(null, Kernel32Util.formatMessageFromLastErrorCode(error), "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			boolean jambo = true;
			
			while (jambo)
				jambo = this.jambo_loco(pid);
			
		}
	
		return result;
	}
	
	private void copiado (File src, File dst) throws IOException
	{
		CopiarArchivo cop = CopiarArchivo.getInstance();
		
		cop.copyDirectory(src, dst);
	}
	
	private boolean jambo_loco (int pid)
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
	
	@Override
	public void mostrar() 
	{
		this.add(this.panel);
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void update() 
	{
		this.setVisible(false);
		this.panel.removeAll();
		this.panel.updateUI();
		this.panel.repaint();
		this.panel.setLayout(new GridLayout(3, 1));
		
		this.panelArriba.removeAll();
		this.panelArriba.updateUI();
		this.panelArriba.repaint();
		this.panelArriba.setLayout(new GridLayout(1, 2));
		
		this.panelMedio.removeAll();
		this.panelMedio.updateUI();
		this.panelMedio.repaint();
		this.panelMedio.setLayout(new GridLayout(1, 2));
		
		this.panelAbajo.removeAll();
		this.panelAbajo.updateUI();
		this.panelAbajo.repaint();
		this.panelAbajo.setLayout(new BorderLayout());
		
		this.panelArriba.add(this.lblRutaIni);
		this.panelArriba.add(this.txtRutaIni);
		
		this.panelMedio.add(this.lblRutaFin);
		this.panelMedio.add(this.txtRutaFin);
		
		this.panelAbajo.add(this.copiado);
		
		this.panel.add(this.panelArriba);
		this.panel.add(this.panelMedio);
		this.panel.add(this.panelAbajo);
		
		this.setTitle("Copiado archivos necesarios");
		this.setSize(480, 150);
		this.add(this.panel);
		this.setVisible(true);
	}

	
	@Override
	public void update2() 
	{
		// TODO Auto-generated method stub
		this.setVisible(false);
		this.panel.removeAll();
		this.panel.updateUI();
		this.panel.repaint();
		this.panel.setLayout(new GridLayout(3, 1));
		this.lblRutaIni = new JLabel ("Introducir ruta instalación actualización:");
		this.txtRutaIni.setText(null);
		this.panel.add(this.lblRutaIni);
		this.panel.add(this.txtRutaIni);
		this.panel.add(this.ejeAct);
		this.setTitle("Actualización TC");
		this.setSize(300, 100);
		this.add(this.panel);
		this.setVisible(true);
	}
	
	
}
