package copiado;

/*import java.io.DataInputStream;
import java.io.DataOutputStream;*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Permite copiar un archivo de un lado a otro
 * @author BillyJoel
 */
public class CopiarArchivo 
{
    /**
     * Instancia de la clase CopiarArchivo
     */
    private static CopiarArchivo copiarArchivo;
   
    /**
     * Constructor de la clase. Inicializa la instancia
     */
    private CopiarArchivo()
    {
       
    }

    /**
     * Método que permite obtener una instancia de la clase CopiarArchivo
     * @return 
     */
    public static CopiarArchivo getInstance()
    {
        if(copiarArchivo == null)
            copiarArchivo = new CopiarArchivo();
        return copiarArchivo;
    }
   
    /**
     * Copia un arhivo de una ubicación a otra.
     * @param origen
     * @param destino
     */
    public void copyDirectory(File srcDir, File dstDir) throws IOException 
    { 
        if (srcDir.isDirectory()) 
        { 
            if (!dstDir.exists())
                dstDir.mkdir();
             
            String[] children = srcDir.list(); 
            for (int i = 0; i < children.length; i++) 
                copyDirectory(new File(srcDir, children[i]), new File(dstDir, children[i]));
        } 
        else
            copy(srcDir, dstDir);
    } 
     
    /** 
     * Copia un solo archivo 
     * @param src 
     * @param dst 
     * @throws IOException 
     */ 
    public void copy(File src, File dst) throws IOException 
    { 
        InputStream in = new FileInputStream(src); 
        OutputStream out = new FileOutputStream(dst);        
         
        byte[] buf = new byte[1024]; 
        int len; 
        while ((len = in.read(buf)) > 0) 
            out.write(buf, 0, len);
        
        in.close(); 
        out.close(); 
    } 
    
    /*
    @SuppressWarnings({ "resource", "deprecation" })
	public void copyFindAndReplace(String source_file, String destination_file, String toFind, String toReplace)
    {
        String str;
        try
        {
            FileInputStream fis2 = new FileInputStream(source_file);
            DataInputStream input = new DataInputStream (fis2);
            FileOutputStream fos2 = new FileOutputStream(destination_file);
            DataOutputStream output = new DataOutputStream (fos2);

            while (((str = input.readLine())) != null)
            {
                String s2=toFind;
                String s3=toReplace;

                int x=0;
                int y=0;
                String result="";
                while ((x=str.indexOf(s2, y))>-1) 
                {
                    result+=str.substring(y,x);
                    result+=s3;
                    y=x+s2.length();
                }
                result+=str.substring(y);
                str=result;

                if(str.indexOf("'',") != -1)
                    continue;
                else
                {
                    str=str+"\n";
                    output.writeBytes(str);
                }
            }
        }
        catch (IOException ioe)
        {
            System.err.println ("I/O Error - " + ioe);
        }
        
    }*/
     
}