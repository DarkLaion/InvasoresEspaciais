package engine;

//imports
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Laionel e Cauê
 */
public class Personagem {

    protected int x, y;
    protected int largura, altura;
    protected String[] colecaoSprites;
    protected Nivel nivel;
    protected GerenciadorSprites gerenciadorSprites;
    protected int frameAtual, velocidadeFrame, t;
    protected boolean remover;

    public Personagem(Nivel nivel) {
        this.nivel = nivel;
        gerenciadorSprites = nivel.getGerenciadorSprites();
    }

    public void desenhar(Graphics2D g) {
        g.drawImage(gerenciadorSprites.getSprite(colecaoSprites[frameAtual]), x, y,
                nivel);
    }

    //getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return altura;
    }

    public int getWidith() {
        return largura;
    }

    public int getVelocidadeFrame() {
        return velocidadeFrame;
    }

    //setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public void setVelocidadeFrame(int velocidadeFrame) {
        this.velocidadeFrame = velocidadeFrame;
    }

    public void setColecaoSprites(String[] nomes) {
        colecaoSprites = nomes;
        altura = 0;
        largura = 0;

        for (int i = 0; i < nomes.length; i++) {
            BufferedImage imagem = gerenciadorSprites.getSprite(colecaoSprites[i]);
            altura = Math.max(altura, imagem.getHeight());
            largura = Math.max(largura, imagem.getWidth());
        }
    }

    public void setRemover() {
        remover = true;
    }

    public boolean remova() {
        return remover;
    }

    public Rectangle getLimites() {
        return new Rectangle(x, y, largura, altura);
    }

    public void colisao(Personagem a) {

    }

    public void acao() {
        t++;
        if (velocidadeFrame % t == 0) {
            t = 0;
            frameAtual = (frameAtual + 1) % colecaoSprites.length;
        }
    }
}
