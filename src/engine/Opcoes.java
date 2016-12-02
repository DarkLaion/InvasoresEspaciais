/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author Laionel
 */
public class Opcoes extends Canvas {

    public Opcoes() {
        JFrame janelaOpcoes = new JFrame("Invasores Espaciais - Opções");

        janelaOpcoes.setBounds(0, 0, 600, 300);
        janelaOpcoes.setVisible(true);
        janelaOpcoes.setResizable(false);
        janelaOpcoes.setLocationRelativeTo(null);
        janelaOpcoes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton btIniciar = new JButton("Iniciar");
        janelaOpcoes.add(btIniciar);
        
        btIniciar.addActionListener(this::btIniciarActionPerformed);
    }
    
    private void btIniciarActionPerformed(ActionEvent evt){                                         
        new Jogo();
    }    
           
    // Metodo principal
    public static void main(String args[]) {
        Opcoes opcoes = new Opcoes();
    }
}
