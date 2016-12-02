package engine;

/**
 * Código baseado no projeto de Soraia Teixeira Barbosa, desenvolvido na FAPERJ
 * como trabalho de conclusao de curso. e-mail: soraiatbarbosa@gmail.com, arcade
 * Space Invaders Autores: Laionel e Cauê.
 */
public class Inimigo extends Personagem {

    protected int vy, vx, vInimigo;

    //getters
    public int getVy() {
        return vy;
    }

    public int getVx() {
        return vx;
    }

    //setters
    public void setVy(int vy) {
        this.vy = vy;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public Inimigo(Nivel nivel) {
        super(nivel);
        setColecaoSprites(new String[]{
            "img/alien01.png", 
            "img/alien02.png", 
        });
        setVelocidadeFrame(35);
    }

    @Override
    public void acao() {
    super.acao();
        x += vx;
        y += vy;
        if (x < 0) {
            vx = -vx;
        }

        if (x > Nivel.WIDTH - getWidith()) {
            vx = -vx;
            vy = vy + 1;
        }

        if (y < 0) {
            vy = 0;
        }
        if (y >= Nivel.PLAY_HEIGHT - getHeight()) {
            nivel.fimJogo();
        }
    }
 
    public void gerarInimigo() {
        Inimigo a = new Inimigo(nivel);
        a.setX((int) (Math.random() * Nivel.PLAY_HEIGHT));
        a.setY((int) (Math.random() * Nivel.PLAY_HEIGHT / 2));
        a.setVx((int)(Math.random() * 30 - 10));
        nivel.addPersonagem(a);
    }

    @Override
    public void colisao(Personagem a) {
        if (a instanceof Tiro) {
            setRemover();
            // Adicionar Pontuação
            nivel.getJogador().addPontuacao(10);
            nivel.getGerenciadorSons().tocarSom("som/defeat.wav");
            gerarInimigo();
            a.setRemover();
        }
        
    }
}