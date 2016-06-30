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
			
			JLabel label = new JLabel("Introduzca contraseña de administrador para " + admin + ":");
			
			JPasswordField pass = new JPasswordField();
			
			try
			{
				String cmd[] = new String [4];
				cmd [0] = "C:/Windows/System32/cmd.exe";
				cmd [1] = "/C";
				cmd [2] = "runas /user:" + admin + " " + rutaIns;
				JOptionPane.showConfirmDialog(null, new Object[]{label, pass}, "Password", JOptionPane.OK_CANCEL_OPTION);
				cmd [3] = String.valueOf(pass.getPassword());

				Process p = Runtime.getRuntime().exec(cmd);
				//p.waitFor();
				p.getOutputStream().write(cmd [3].getBytes());
				p.getInputStream().read(cmd [3].getBytes());
				
				InputStream is = p.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				
				String line;
				
				while ((line = br.readLine()) != null) 
				{
					JOptionPane.showMessageDialog(null, line, "Alerta", JOptionPane.INFORMATION_MESSAGE);
				}
								
				br.close();
				
			}
			catch (Exception e1)
			{
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
}
