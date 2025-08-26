package edu.eci.arsw.primefinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainCanodromo {

    private static Galgo[] galgos;
    private static Canodromo can;
    private static RegistroLlegada reg = new RegistroLlegada();
    private static final Pausa pausa = new Pausa(); // monitor de pausa compartido

    public static void main(String[] args) {
        can = new Canodromo(17, 100);
        galgos = new Galgo[can.getNumCarriles()];
        can.setVisible(true);

        // Acción del botón start
        can.setStartAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                // deshabilita el botón Start
                ((JButton) e.getSource()).setEnabled(false);

                // corre en un hilo aparte para no bloquear el EDT
                new Thread() {
                    public void run() {
                        // crear y arrancar galgos, pasando el monitor de pausa
                        for (int i = 0; i < can.getNumCarriles(); i++) {
                            galgos[i] = new Galgo(can.getCarril(i), "" + i, reg, pausa);
                            galgos[i].start();
                        }

                        // esperar a que todos terminen
                        for (int i = 0; i < can.getNumCarriles(); i++) {
                            try {
                                galgos[i].join();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                                // restaurar el estado de interrupción si fuese necesario
                                Thread.currentThread().interrupt();
                            }
                        }

                        // ahora sí, mostrar ganador
                        can.winnerDialog(reg.getGanador(), reg.getUltimaPosicionAlcanzada() - 1);
                        System.out.println("El ganador fue: " + reg.getGanador());
                    }
                }.start();
            }
        });

        // Acción del botón stop -> pausa la carrera
        can.setStopAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pausa.pause();
                System.out.println("Carrera pausada!");
            }
        });

        // Acción del botón continue -> reanuda la carrera
        can.setContinueAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pausa.resumeAll();
                System.out.println("Carrera reanudada!");
            }
        });
    }
}

