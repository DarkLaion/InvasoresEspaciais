package engine;

/**
 * Código baseado no projeto de Soraia Teixeira Barbosa, desenvolvido na FAPERJ
 * como trabalho de conclusao de curso. e-mail: soraiatbarbosa@gmail.com, arcade
 * Space Invaders Autores: Laionel e Cauê.
 */
public class Tiro extends Personagem {

    protected static final int SHOT_SPEED = 10;

    public Tiro(Nivel nivel) {
        super(nivel);
        setColecaoSprites(new String[]{"img/shot.png"});
    }

    @Override
    public void acao() {
        super.acao();
        y -= SHOT_SPEED;

        if (y < 0) {
            setRemover();
        }
    }
}