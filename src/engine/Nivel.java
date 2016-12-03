package engine;

//imports
import java.awt.image.ImageObserver;

/**
 *
 * @author Laionel e Cauê
 */
public interface Nivel extends ImageObserver {

    public static final int 
            MINWIDTH = 1000,
            MINHEIGHT = 700,
            MAXWIDTH = 1000,
            MAXHEIGHT = 700,
            WIDTH = 1000,
            HEIGHT = 700,
            PLAY_HEIGHT = HEIGHT - 80,
            SPEED = 10;
    
    public GerenciadorSprites getGerenciadorSprites();
    
    public GerenciadorSons getGerenciadorSons(); 

    public Jogador getJogador();

    public void addPersonagem(Personagem a);

    public void fimJogo();
}