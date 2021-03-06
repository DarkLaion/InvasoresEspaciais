package engine;

//imports
import com.sun.glass.ui.Application;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author Laionel e Cauê
 */
public class Jogador extends Personagem {

    protected int vx;
    protected static final int PLAYER_SPEED = 4;
    private boolean esquerda, direita;
    private int pontuacao = 0;

    private String imgNave = JogoOpcoes.getImgNave();

    //getters
    public int getVx() {
        return vx;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    //setters
    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public void addPontuacao(int pontuacao) {
        this.pontuacao += pontuacao;
    }

    public Jogador(Nivel nivel) {
        super(nivel);
        setColecaoSprites(new String[]{imgNave});
    }

    @Override
    public void acao() {
        super.acao();
        x += vx;
        if (x <= 0) {
            x = 0;
        }
        if (x >= Nivel.WIDTH - getWidith()) {
            x = Nivel.WIDTH - getWidith();
        }
    }

    protected void atualizarVelocidade() {
        if (esquerda) {
            setVx(-PLAYER_SPEED);
        }
        if (direita) {
            setVx(PLAYER_SPEED);
        }
    }

    public void teclaLiberada(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                esquerda = false;
                break;
            case KeyEvent.VK_RIGHT:
                direita = false;
                break;
        }
        atualizarVelocidade();
    }

    public void teclaPressionada(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                esquerda = true;
                break;
            case KeyEvent.VK_RIGHT:
                direita = true;
                break;
            case KeyEvent.VK_SPACE:
                atirar();
                break;
            case KeyEvent.VK_N:

                break;
            case KeyEvent.VK_ESCAPE:
                int resp = JOptionPane.showConfirmDialog(null,
                        "Deseja sair do Jogo?",
                        "Invasores Espaciais", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                break;
        }
        atualizarVelocidade();
    }

    public void atirar() {
        Tiro tiro = new Tiro(nivel);
        tiro.setX(x + 25);
        tiro.setY(y - tiro.getHeight());
        nivel.addPersonagem(tiro);
        nivel.getGerenciadorSons().tocarSom("som/shot.wav");
    }

    @Override
    public void colisao(Personagem a) {
        if (a instanceof Inimigo) {
            nivel.fimJogo();
        }
    }
}
