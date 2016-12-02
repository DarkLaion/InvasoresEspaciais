package engine;

//imports
import java.net.URL;
import java.util.HashMap;

/**
 * Código baseado no projeto de Soraia Teixeira Barbosa, desenvolvido na FAPERJ
 * como trabalho de conclusao de curso. e-mail: soraiatbarbosa@gmail.com, arcade
 * Space Invaders Autores: Laionel e Cauê.
 */
public abstract class CacheRecursos {

    protected HashMap<String, Object> recursos;

    public CacheRecursos() {
        recursos = new HashMap<>();
    }

    protected Object carregarRecurso(String caminhoRecurso) {
        URL url;
        url = getClass().getClassLoader().getResource(caminhoRecurso);
        return carregarRecurso(url);
    }

    protected Object getRecurso(String caminhoRecurso) {
        Object res = recursos.get(caminhoRecurso);
        if (res == null) {
            res = carregarRecurso("recursos/" + caminhoRecurso);
            recursos.put(caminhoRecurso, res);
        }
        return res;
    }

    protected abstract Object carregarRecurso(URL url);
}
