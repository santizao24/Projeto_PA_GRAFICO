package Enums;

/**
 * Enumeração que define os estados possíveis de uma reparação no ciclo de vida
 * do processo.
 *
 * @author Hugo
 * @version 1.0
 */
public enum EstadoReparacao {
    CRIADO,
    ACEITE,
    DECORRER,
    FINALIZADO,
    REJEITADO,
    ARQUIVADO;
}
