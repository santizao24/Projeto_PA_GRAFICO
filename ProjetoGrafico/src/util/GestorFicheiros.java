package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Classe utilitária para gestão de ficheiros físicos, garantindo a separação
 * da lógica de Input/Output (I/O) da interface gráfica (View).
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class GestorFicheiros {

    /**
     * Copia a foto de perfil escolhida pelo utilizador para a diretoria interna da
     * aplicação.
     *
     * @param fotoOrigem     o ficheiro original escolhido
     * @param caminhoDestino o caminho (incluindo o nome do ficheiro) onde guardar
     * @return true se a cópia for bem-sucedida, false caso contrário
     */
    public static boolean guardarFotoPerfil(File fotoOrigem, String caminhoDestino) {
        if (fotoOrigem == null) {
            return false;
        }

        File destino = new File(caminhoDestino);

        File parent = destino.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(fotoOrigem);
            fos = new FileOutputStream(destino);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (fis != null)
                    fis.close();
                if (fos != null)
                    fos.close();
            } catch (Exception ex) {

            }
        }
    }
}
