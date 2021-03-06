package mvc.vista;

public interface Vista 
{
	
	/**
	 * Muestra la ventana inicial.
	 */
	public void mostrar ();

	/**
	 * Muestra la ventana actualizada, tras la ejecución del proceso,
	 * donde se podrán introducir los directorios fuente y destino de
	 * donde se copiarán los archivos.
	 */
	public void update ();	

	/**
	 * Segunda actualización de la ventana, esta vez con los datos previos a la actualización
	 * para lanzar el proceso de la actualización, indicando la ruta donde se encuentra.
	 */
	public void update2 ();
	
	/**
	 * Para mostrar mensaje de error en la ventana.
	 * @param error
	 */
	public void updateError (int error);
	
	/**
	 * Para mostrar el mensaje de error de copiado de archivos.
	 * @param mensaje
	 */
	public void updateErrorCopy (String mensaje);
}
