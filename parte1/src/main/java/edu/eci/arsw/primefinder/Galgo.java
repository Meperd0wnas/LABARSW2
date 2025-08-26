package edu.eci.arsw.primefinder;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
    private int paso;
    private Carril carril;
    RegistroLlegada regl;
    private Pausa pausa; // nuevo

    public Galgo(Carril carril, String name, RegistroLlegada reg, Pausa pausa) {
        super(name);
        this.carril = carril;
        paso = 0;
        this.regl = reg;
        this.pausa = pausa;
    }

    public void corra() throws InterruptedException {
        while (paso < carril.size()) {
            // chequeo de pausa antes de dormir/avanzar
            pausa.esperaSiPausado();

            Thread.sleep(100);

            // chequeo de pausa después de dormir (mejora el tiempo de respuesta)
            pausa.esperaSiPausado();

            carril.setPasoOn(paso++);
            carril.displayPasos(paso);

            if (paso == carril.size()) {
                carril.finish();

                int ubicacion;
                synchronized (regl) {
                    ubicacion = regl.getUltimaPosicionAlcanzada();
                    regl.setUltimaPosicionAlcanzada(ubicacion + 1);
                    if (ubicacion == 1) {
                        regl.setGanador(this.getName());
                    }
                }
                System.out.println("El galgo " + this.getName() + " llego en la posicion " + ubicacion);
            }
        }
    }

    @Override
    public void run() {
        try {
            corra();
        } catch (InterruptedException e) {
            // opcional: manejar interrupciones (print o restaurar estado)
            e.printStackTrace();
        }
    }
}
