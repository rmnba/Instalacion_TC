package ventana;

import java.io.File;
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

import com.sun.jna.platform.win32.Kernel32Util;

import control.Controlador;

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
	
	private JPasswordField pass;
	
	private Controlador control;
	
	/**
	 * Constructor, que inicializa la Ventana
	 */
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
		
		this.pass = new JPasswordField();
		
		this.control = new Controlador(this);
	}

	/**
	 * Método para las acciones de los botones de la ventana.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("Ejecutar"))
		{			
			JLabel label = new JLabel("Escriba la contraseña para " + this.nombre.getText() + ":");
			
			JOptionPane.showConfirmDialog(null, new Object[]{label, this.pass}, "Password", JOptionPane.OK_CANCEL_OPTION);
			
			// Ejecución de la instalación
			control.run(this.nombre.getText(), String.valueOf(this.pass.getPassword()), this.ruta.getText());
			
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
			control.copiado(new File(this.txtRutaIni.getText()), new File(this.txtRutaFin.getText()));
		}
		else
		{
			this.pass.setText(null);
			
			JLabel label = new JLabel("Escriba la contraseña para " + this.nombre.getText() + ":");
			
			JOptionPane.showConfirmDialog(null, new Object[]{label, this.pass}, "Password", JOptionPane.OK_CANCEL_OPTION);
			
			// Ejecución de la actualización
			control.runAct(this.nombre.getText(), String.valueOf(this.pass.getPassword()), this.txtRutaIni.getText());
			
		}
	}
	
	/**
	 * Método que muestra ventana diciendo si la instalación fue o no correcta
	 * @param correcto
	 */
	public void resultadoInstalacion (boolean correcto)
	{
		this.setVisible(false);
		
		if (correcto)
			JOptionPane.showMessageDialog(null, "Instalación correcta", "Correcto", JOptionPane.INFORMATION_MESSAGE);

		else
			JOptionPane.showMessageDialog(null, "Instalación errónea", "Error", JOptionPane.ERROR_MESSAGE);
		
		System.exit(0);
	}

	@Override
	public void mostrar() 
	{
		// TODO Auto-generated method stub
		
		this.add(this.panel);
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
		
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

	@Override
	public void updateError(int error) 
	{
		// TODO Auto-generated method stub
	    JOptionPane.showMessageDialog(null, Kernel32Util.formatMessageFromLastErrorCode(error), "Error", JOptionPane.ERROR_MESSAGE);		
	}

	@Override
	public void updateErrorCopy(String mensaje) 
	{
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	
}
