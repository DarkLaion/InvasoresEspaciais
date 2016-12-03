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
import javax.swing.border.*;

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

    private static Jogo jogoAtual;
    private static JFrame janelaAtual;
    private static JComboBox comboNave;
    private static String imgNave;

    public Jogo() {
        gerenciadorSprites = new GerenciadorSprites();
        gerenciadorSons = new GerenciadorSons();

        JFrame janelaJogo = new JFrame("Invasores Espaciais");
        janelaJogo.setBounds(0, 0, Nivel.WIDTH, Nivel.HEIGHT);
        janelaJogo.setVisible(true);
        janelaJogo.setResizable(false);
        janelaJogo.setLocationRelativeTo(null);
        janelaJogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = (JPanel) janelaJogo.getContentPane();
        setBounds(0, 0, Nivel.WIDTH, Nivel.HEIGHT);
        panel.setPreferredSize(new Dimension(Nivel.WIDTH, Nivel.HEIGHT));
        panel.setLayout(null);
        panel.add(this);

        createBufferStrategy(3);
        estrategia = getBufferStrategy();

        janelaJogo.setIgnoreRepaint(true);
        janelaJogo.requestFocus();
        janelaJogo.addKeyListener(this);
    }

    //Nivel
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
        jogador.setY(Nivel.HEIGHT - 1 * jogador.getHeight() - 84);
        gerenciadorSons.tocarSom("som/launch.wav");
        gerenciadorSons.repetirSom("som/fundo.wav");
    }

    // adicionar personagem
    @Override
    public void addPersonagem(Personagem a) {
        personagens.add(a);
    }

    // atualizar nivel
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

    // checar colisao
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
    // fim do jogo
    @Override
    public void fimJogo() {
        fimJogo = true;
        gerenciadorSons.pararSom();
    }

    //Metodo que desenha as intruções ,pontuação, nivel e fim
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
        g.drawString("PONTOS:", 20, Nivel.PLAY_HEIGHT + 25);
        // Cor da fonte pontos
        g.setPaint(Color.YELLOW);
        g.drawString(jogador.getPontuacao() + "", 120, Nivel.PLAY_HEIGHT + 25);
        //Instruções
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.setPaint(Color.WHITE);
        g.drawString("ESPAÇO Atirar, <- Esquerda, Direita ->, ESC Sair",
               this.getWidth()/2, Nivel.PLAY_HEIGHT + 35);
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
        g.fillRect(0, 0, Nivel.WIDTH, Nivel.HEIGHT - 80);
        // Cor da Fonte
        g.setColor(Color.RED);
        // Tipo e tamanho da fonte
        g.setFont(new Font("Arial", Font.BOLD, 60));
        // Menssage de fim de jogo
        g.drawString("Fim do Jogo", Nivel.WIDTH / 3, Nivel.HEIGHT / 2);
        estrategia.show();
    }
    
    // getters
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

    public static JFrame getJanelaAtual() {
        return janelaAtual;
    }

    public static Jogo getJogoAtual() {
        return jogoAtual;
    }
    
    public String getImgNave() {
        if ("".equals(imgNave)){
            return imgNave = "recursos/img/ship01.png";//Padrao
        }else{
        return imgNave;
        }
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
       
    // Metodo pricipal
    public static void main(String[] args) {

        Jogo novoJogo = new Jogo();
        jogoAtual = novoJogo;
        novoJogo.opcoes();
    }

    //janela de opções - contem erros
    public void opcoes() { 
        JFrame janelaOp = new JFrame();
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        JComboBox comboPlayer = new JComboBox<>();
        JButton btIniciar = new JButton();

        //propriedades da janela
        janelaOp.setLayout(null);
        janelaOp.setTitle("Invasores Espaciais: Opções");
        janelaOp.setBounds(0, 0, 500, 300);
        janelaOp.setVisible(true);
        janelaOp.setResizable(false);
        janelaOp.setLocationRelativeTo(null);
        janelaOp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //propriedades do panel
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        panel.setBounds(10, 10, janelaOp.getWidth() - 28, janelaOp.getHeight() - 50);

        //propriedades do label
        label.setText("Selecione sua nave:");
        label.setSize(150, 30);
        label.setLabelFor(comboPlayer);
        label.setLocation(20, 70);

        //propriedades do combo
        comboPlayer.setSize(150, 30);
        comboPlayer.setLocation(20, 100);
        comboPlayer.setModel(new DefaultComboBoxModel<>(
                new String[]{
                    "Imperium",
                    "Falcon"}
        ));
        comboNave = comboPlayer;
        comboPlayer.addActionListener(this::comboPlayerActionPerformed);

        //propriedades do botao
        btIniciar.setText("Iniciar Jogo");
        btIniciar.addActionListener(this::btIniciarActionPerformed);
        btIniciar.setSize(400, 50);
        btIniciar.setLocation(
                panel.getWidth() / 2 - btIniciar.getWidth() / 2,
                panel.getHeight() - btIniciar.getHeight() - 10
        );

        //Adicionar componentes
        janelaOp.add(panel);
        panel.add(label);
        panel.add(comboPlayer);
        panel.add(btIniciar);

        imgNave = "img/ship01.png"; //ship 01 = Falcom
        janelaAtual = janelaOp;
    }

    private void btIniciarActionPerformed(ActionEvent evt) {
        janelaAtual.dispose();
        jogoAtual.setIgnoreRepaint(true);
        jogoAtual.requestFocus();
        jogoAtual.addKeyListener(this);
        jogoAtual.jogo();
        
    }

    private void comboPlayerActionPerformed(ActionEvent evt) {
        if (comboNave.getSelectedIndex() == 0) {
            imgNave = "img/ship01.png"; //ship 01 = Falcom
        } else if (comboNave.getSelectedIndex() == 1) {
            imgNave = "img/ship02.png"; //ship 02 = Imperium
        }
    }
        @Override
    public void keyPressed(KeyEvent e) {
        jogador.teclaPressionada(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        jogador.teclaLiberada(e);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {

    }
}
