# Lab ARSW2

## Daniel Ricardo Ruge Gomez

### Parte I

    1.

![alt text](/img/media/CPU.png)

    El procesador del equipo en el que estoy trabajandoo cuenta con dos nucleos, como el programa solo cuenta con un hilo lo que pasa es que un solo núcleo lógico está saturado con la tarea de calcular primos, mientras que el otro en teoria no está haciendo un trabajo significativo.

    2.

![alt text](/img/media/claseMod.png)

    uso de los procesadores 
    
![alt text](/img/media/CPU2.png)

    Aqui ya en teoria se puede ver como al usar tres nucleos la carga para cada nucleo resulta mas equitativa:

### Parte II

    1.

    correccion del codigo para que los hilos terminen antes de mostrar el ganador con join().

![alt text](/img/media/correccion1.png)



    prueba de la corrección

![alt text](/img/media/carrera.png)

    2.

    inconsistencia :

![alt text](/img/media/inconsistencia1.png)

    El problema (la region critica) esta en esta clase ya que dos o mas hilos pueden leer el mismo valor de ultimaPosicionAlcanzada  antes de que alguno lo actualice (condición de carrera).

![alt text](/img/media/problema.png)


    3.
    
    metodo galgo.corra() actualizado:

![alt text](/img/media/galgoCorra.png)

    hice el bloqueo en esta clase y no en RegistroLlegada por que La idea es que el bloqueo sea lo más atómico y corto posible


    4.

![alt text](/img/media/carreraPausa.png)

    cree la clase Pausa como monitor (con pause(), resumeAll() y esperaSiPausado() que usan synchronized, wait() y notifyAll()), luego modifique Galgo para recibir un Pausa en su constructor y llamar a pausa.esperaSiPausado() en puntos estratégicos del bucle corra() (antes/después del sleep) para que los hilos se suspendan y reanuden correctamente, y por ultimo actualize MainCanodromo haciendo pausa static final, pasando esa misma instancia a cada Galgo al arrancarlos y cambiando los ActionListener de los botones Stop/Continue para invocar pausa.pause() y pausa.resumeAll() respectivamente; la región crítica de RegistroLlegada se mantuvo igual para conservar la sincronización de las llegadas.


### Notas

    Tuve un problema con la carpeta de la parte 2 del laboratorio, por lo cual me tocó mezclarlo todo en la carpeta de la parte uno; sin embargo, ambos ejercicios funcionan correctamente.
