package engine;

//imports
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.ActionEvent;


/**
 * Código baseado no projeto de Soraia Teixeira Barbosa, desenvolvido na FAPERJ
 * como trabalho de conclusao de curso. e-mail: soraiatbarbosa@gmail.com, arcade
 * Space Invaders Autores: Laionel e Cauê.
 */
@SuppressWarnings("serial")
public class Jogo extends Canvas implements Nivel, KeyListener {

    private BufferStrategy estrategia;
    private GerenciadorSprites gerenciadorSprites;
    private GerenciadorSons gerenciadorSons;
    private ArrayList<Personagem> personagens;
    private Jogador jogador;
    @SuppressWarnings("unused")
    private long tempo;
    private boolean fimJogo = false;
    
    public Jogo() {
        gerenciadorSprites = new GerenciadorSprites();
        gerenciadorSons = new GerenciadorSons();
        
        JFrame janelaJogo = new JFrame("Invasores Espaciais");

        JPanel panel = (JPanel) janelaJogo.getContentPane();
        setBounds(0, 0, Nivel.WIDTH, Nivel.HEIGHT);
        panel.setPreferredSize(new Dimension(Nivel.WIDTH, Nivel.HEIGHT));
        panel.setLayout(null);
        panel.add(this);

        janelaJogo.setBounds(0, 0, Nivel.WIDTH, Nivel.HEIGHT);
        janelaJogo.setVisible(true);
        janelaJogo.setResizable(false);
        janelaJogo.setLocationRelativeTo(null);
        janelaJogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createBufferStrategy(2);
        estrategia = getBufferStrategy();

        setIgnoreRepaint(true);
        janelaJogo.requestFocus();
        janelaJogo.addKeyListener(this);
        
        
    }
    
    public void iniciarNivel() {
        personagens = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Inimigo inimigo = new Inimigo(this);
            inimigo.setX(i + 10);
            inimigo.setY(i * 20);
            inimigo.setVx((int) (Math.random() * 20 - 10));
            personagens.add(inimigo);
        }

        jogador = new Jogador(this);
        jogador.setX(Nivel.WIDTH / 2);
        jogador.setY(Nivel.HEIGHT - 1 * jogador.getHeight()-84);
        gerenciadorSons.tocarSom("som/launch.wav");
        gerenciadorSons.repetirSom("som/fundo.wav");
    }

    @Override
    public void addPersonagem(Personagem a) {
        personagens.add(a);
    }

    public void atualizarNivel() {
        int i = 0;

        while (i < personagens.size()) {
            Personagem inimigo = (Personagem) personagens.get(i);

            if (inimigo.remova()) {
                personagens.remove(i);
            } else {
                inimigo.acao();
                i++;
            }
        }
        jogador.acao();
    }

    public void checarColisao() {
            
        Rectangle limitesJogador = jogador.getLimites();
        
        for (int i = 0; i < personagens.size(); i++) {
            Personagem a1 = (Personagem) personagens.get(i);
            Rectangle r1 = a1.getLimites();

            if (r1.intersects(limitesJogador)) {
                jogador.colisao(a1);
                a1.colisao(jogador);
            }

            for (int j = i + 1; j < personagens.size(); j++) {
                Personagem a2 = (Personagem) personagens.get(j);
                Rectangle r2 = a2.getLimites();

                if (r1.intersects(r2)) {
                    a1.colisao(a2);
                    a2.colisao(a1);
                }
            }
        }
    }

    @Override
    public void fimJogo() {
        fimJogo = true;
    }

    //Metodo que desenha a pontuação, nivel e fim
    public void desenharPontuacao(Graphics2D g) {
        // Cor de fundo
        g.setColor(Color.GRAY);
        // Posição e tamanho
        g.fillRect(0, Nivel.PLAY_HEIGHT, Nivel.WIDTH,
                (Nivel.HEIGHT - Nivel.PLAY_HEIGHT));
        // Tipo e tamanho da fonte
        g.setFont(new Font("Arial", Font.BOLD, 20));
        // Cor da fonte texto
        g.setPaint(Color.BLACK);
        g.drawString("PONTOS:", 20, Nivel.PLAY_HEIGHT + 20);
        // Cor da fonte pontos
        g.setPaint(Color.YELLOW);
        g.drawString(jogador.getPontuacao() + "", 120, Nivel.PLAY_HEIGHT + 20);
    }

    public void desenharNivel() {
        Graphics2D g = (Graphics2D) estrategia.getDrawGraphics();
        // Cor da Fonte
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < personagens.size(); i++) {
            Personagem m = (Personagem) personagens.get(i);
            m.desenhar(g);
        }

        jogador.desenhar(g);
        desenharPontuacao(g);
        estrategia.show();
    }

    public void desenharFimJogo() {
        Graphics2D g = (Graphics2D) estrategia.getDrawGraphics();
        // Cor do retangulo
        g.setColor(Color.BLACK);
        // Tamanho do retangulo desenhado
        g.fillRect(0, 0, Nivel.WIDTH, Nivel.HEIGHT-80);
        // Cor da Fonte
        g.setColor(Color.RED);
        // Tipo e tamanho da fonte
        g.setFont(new Font("Arial", Font.BOLD, 60));
        // Menssage de fim de jogo
        g.drawString("Fim do Jogo", Nivel.WIDTH /3 , Nivel.HEIGHT / 2);
        estrategia.show();
    }

    @Override
    public GerenciadorSprites getGerenciadorSprites() {
        return gerenciadorSprites;
    }

    @Override
    public GerenciadorSons getGerenciadorSons() {
        return gerenciadorSons;
    }

    @Override
    public Jogador getJogador() {
        return jogador;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        jogador.teclaLiberada(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        jogador.teclaPressionada(e);
    }
    
    public void jogo() {
        tempo = 1000;
        iniciarNivel();

        while (isVisible() && !fimJogo) {

            long tempoInicio = System.currentTimeMillis();
            atualizarNivel();
            checarColisao();
            desenharNivel();

            tempo = System.currentTimeMillis() - tempoInicio;

            try {
                // Pause
                Thread.sleep(17);
            } catch (InterruptedException e) {
                // Possiveis erros declarar aqui
            }
        }
        desenharFimJogo();
    }


    public static void main(String[] args){
        Jogo inv = new Jogo();
        inv.jogo();  
    }
    
    public void opcoes(){
        JFrame janelaOp = new JFrame("Invasores Espaciais: Opções");
        
    }
    
    private void btIniciarActionPerformed(ActionEvent evt){                                         
       
    }   
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}