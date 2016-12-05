package engine;

//imports
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Laionel e Cauê
 */
public class JogoOpcoes extends JFrame {

    private GerenciadorSprites gerenciadorSprites;
    private static String imgNave, imgLogo;
    private static JFrame janelaOp;
    private static JPanel panel;
    private static JLabel label, labelLogo, labelNave;
    private static JComboBox comboPlayer;
    private static JButton btIniciar;
    private ImageIcon imagem;
    private Boolean jogar;

    public JogoOpcoes() {
        jogar = false;
        gerenciadorSprites = new GerenciadorSprites();
        janelaOp = new JFrame();
        panel = new JPanel();
        label = new JLabel();
        labelLogo = new JLabel();
        labelNave = new JLabel();
        comboPlayer = new JComboBox<>();
        btIniciar = new JButton();

        //propriedades da janela
        janelaOp.setLayout(null);
        janelaOp.setTitle("Invasores Espaciais: Opções");
        janelaOp.setBounds(0, 0, 500, 300);
        janelaOp.setVisible(true);
        janelaOp.setResizable(false);
        janelaOp.setLocationRelativeTo(null);
        janelaOp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //propriedades do panel
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        panel.setBounds(10, 10, janelaOp.getWidth() - 28, janelaOp.getHeight() - 50);

        //propriedades do label:Logo
        labelLogo.setLocation(40, 20);

        //propriedades do label:Nave
        labelNave.setLayout(null);
        labelNave.setLocation(327, 30);
        labelNave.setHorizontalAlignment(SwingConstants.CENTER);
        labelNave.setVerticalAlignment(SwingConstants.CENTER);
        labelNave.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        //propriedades do label
        label.setText("Selecione sua nave:");
        label.setSize(140, 30);
        label.setLabelFor(comboPlayer);
        label.setLocation(40, 140);

        //propriedades do combo
        comboPlayer.setSize(120, 30);
        comboPlayer.setLocation(175, 140);
        comboPlayer.setModel(new DefaultComboBoxModel<>(
                new String[]{
                    "Imperium",
                    "Falcon"}
        ));
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
        panel.add(labelLogo);
        panel.add(labelNave);
        panel.add(comboPlayer);
        panel.add(btIniciar);

        //caminho das imagens
        imgNave = "img/ship01.png"; //padrao
        imgLogo = "img/logo.png"; //logo

        setImgLogo();
        setImgNave();
    }

    public void setImgNave() {
        imagem = new ImageIcon(gerenciadorSprites.getSprite(imgNave));
        labelNave.setSize(imagem.getIconWidth() + 40, imagem.getIconHeight() + 60);
        labelNave.setIcon(imagem);
    }

    public void setImgLogo() {
        imagem = new ImageIcon(gerenciadorSprites.getSprite(imgLogo));
        labelLogo.setSize(imagem.getIconWidth(), imagem.getIconHeight());
        labelLogo.setIcon(imagem);
    }

    public void setJogar() {
        jogar = true;
    }

    public static String getImgNave() {
        return imgNave;
    }

    public Boolean getJogar() {
        return jogar;
    }

    private void btIniciarActionPerformed(ActionEvent evt) {
        setJogar();
        janelaOp.dispose();
    }

    private void comboPlayerActionPerformed(ActionEvent evt) {
        if (comboPlayer.getSelectedIndex() == 0) {
            imgNave = "img/ship01.png"; //ship 01 = Falcom
        } else if (comboPlayer.getSelectedIndex() == 1) {
            imgNave = "img/ship02.png"; //ship 02 = Imperium
        }
        setImgNave();
    }

    /*public static void main(String[] args) {
        new JogoOpcoes();
    }*/
}
