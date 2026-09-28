package com.fieldops.application;

import com.fieldops.ui.FieldOpsFrame;

import javax.swing.SwingUtilities;

// Ponto de entrada da aplicacao: e por aqui que o programa comeca a executar.
public class Main {
    public static void main(String[] args) {
        // SwingUtilities garante que a interface seja criada na thread correta do Swing.
        SwingUtilities.invokeLater(() -> {
            FieldOpsData data = SampleData.create();
            FieldOpsFrame frame = new FieldOpsFrame(data);
            frame.setVisible(true);
        });
    }
}
