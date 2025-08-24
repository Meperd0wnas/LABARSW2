package edu.eci.arsw.primefinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainCanodromo {

    private static Galgo[] galgos;

    private static Canodromo can;

    private static RegistroLlegada reg = new RegistroLlegada();

    public static void main(String[] args) {
        can = new Canodromo(17, 100);
        galgos = new Galgo[can.getNumCarriles()];
        can.setVisible(true);

        // Acción del botón start
        can.setStartAction(
            new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    // deshabilita el botón
                    ((JButton) e.getSource()).setEnabled(false);

                    // corre en un hilo aparte
                    new Thread() {
                        public void run() {
                            for (int i = 0; i < can.getNumCarriles(); i++) {
                                galgos[i] = new Galgo(can.getCarril(i), "" + i, reg);
                                galgos[i].start();
                            }

                            // esperar a que todos terminen
                            for (int i = 0; i < can.getNumCarriles(); i++) {
                                try {
                                    galgos[i].join();
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }

                            // ahora sí, mostrar ganador
                            can.winnerDialog(reg.getGanador(), reg.getUltimaPosicionAlcanzada() - 1);
                            System.out.println("El ganador fue: " + reg.getGanador());
                        }
                    }.start();
                }
            }
        );

        can.setStopAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Carrera pausada!");
                    }
                }
        );

        can.setContinueAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Carrera reanudada!");
                    }
                }
        );
    }
}

