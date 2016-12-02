package engine;

//imports
import java.awt.event.KeyEvent;

/**
 * Código baseado no projeto de Soraia Teixeira Barbosa, desenvolvido na FAPERJ
 * como trabalho de conclusao de curso. e-mail: soraiatbarbosa@gmail.com, arcade
 * Space Invaders Autores: Laionel e Cauê.
 */
public class Jogador extends Personagem {

    protected int vx;
    protected static final int PLAYER_SPEED = 4;
    private boolean esquerda, direita;
    private int pontuacao = 0;
    private Jogo jogoAtual = Jogo.getJogoAtual();

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
        setColecaoSprites(new String[]{jogoAtual.getImgNave()});
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
                esquerda = true;
                break;
            case KeyEvent.VK_RIGHT: 
                direita = true;
                break;
        }
        atualizarVelocidade();
    }

    public void teclaPressionada(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: 
                esquerda = false;
                break;
            case KeyEvent.VK_RIGHT: 
                direita = false;
                break;
            case KeyEvent.VK_SPACE: 
                atirar();
                break;
            case KeyEvent.VK_N:
                jogoAtual.jogo();
                break;
            case KeyEvent.VK_S:
                
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