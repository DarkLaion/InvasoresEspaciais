package engine;

//imports
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * Código baseado no projeto de Soraia Teixeira Barbosa, desenvolvido na FAPERJ
 * como trabalho de conclusao de curso. e-mail: soraiatbarbosa@gmail.com, arcade
 * Space Invaders Autores: Laionel e Cauê.
 */
public class GerenciadorSprites extends CacheRecursos {

    @Override
    protected Object carregarRecurso(URL url) {
        try {
            return ImageIO.read(url);
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, 
                    "Erro ao carregar recursos do jogo!"
                        + "\nPor favor, verifique a instalação.", 
                    "Invasores Espaciais", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return null;
        }
    }

    public BufferedImage getSprite(String nome) {
        return (BufferedImage) getRecurso(nome);
    }
}