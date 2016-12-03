package engine;

/**
 *
 * @author Laionel e Cauê
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