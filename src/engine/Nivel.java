package engine;

//imports
import java.awt.image.ImageObserver;

/**
 * Código baseado no projeto de Soraia Teixeira Barbosa, desenvolvido na FAPERJ
 * como trabalho de conclusao de curso. e-mail: soraiatbarbosa@gmail.com, arcade
 * Space Invaders Autores: Laionel e Cauê.
 */
public interface Nivel extends ImageObserver {

    public static final int 
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