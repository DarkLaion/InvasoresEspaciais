package engine;

//imports
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
/**
 *
 * @author Laionel e Cauê
 */
public class JogoOpcoes extends JFrame{
    private static String imgNave;
    private static JFrame janelaOp;
    private static JPanel panel;
    private static JLabel label;
    private static JComboBox comboPlayer;
    private static JButton btIniciar;
    
    
    public JogoOpcoes(){
        janelaOp = new JFrame();
        panel = new JPanel();
        label = new JLabel();
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
        
        imgNave = "img/ship01.png"; //padrao
    }
   
    public static String getImgNave(){
        return imgNave;
    }
        
    private void btIniciarActionPerformed(ActionEvent evt) {
        janelaOp.dispose();
        
        Jogo novoJogo = new Jogo();
        novoJogo.jogo();                    
    }
   
    private void comboPlayerActionPerformed(ActionEvent evt) {
        if (comboPlayer.getSelectedIndex() == 0) {
            imgNave = "img/ship01.png"; //ship 01 = Falcom
        } else if (comboPlayer.getSelectedIndex() == 1) {
            imgNave = "img/ship02.png"; //ship 02 = Imperium
        }
    }
    
    public static void main(String [] args){
        JogoOpcoes jogoOpcoes = new JogoOpcoes();
    }
}
