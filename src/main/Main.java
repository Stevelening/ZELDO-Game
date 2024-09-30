package main;

import javax.swing.*;

public class Main {
    public static Boolean VICTOIRE = false;
    public static Boolean DEFAITE = false;
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(GraphiqueInterface::new);
    }
}
