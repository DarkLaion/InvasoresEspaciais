package engine;

//imports
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JOptionPane;

/**
 *
 * @author Laionel e Cauê
 */
public class GerenciadorSons extends CacheRecursos {

    @Override
    protected Object carregarRecurso(URL url) {
        try{
            return Applet.newAudioClip(url);
        }catch(Exception e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, 
                    "Erro ao carregar recursos do jogo!"
                        + "\nPor favor, verifique a instalação.", 
                    "Invasores Espaciais", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return null;
        }
    }

    public AudioClip getAudio(String nome) {
        return (AudioClip) getRecurso(nome);
    }

    public void tocarSom(final String nome) {
        new Thread(getAudio(nome)::play).start();
    }

    public void repetirSom(final String nome) {
        new Thread(getAudio(nome)::loop).start();
    }
    
    public void pararSom(){
        
    }
}