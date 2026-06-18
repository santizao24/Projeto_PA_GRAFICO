package util;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Classe utilitária que contém métodos estáticos para validação de dados de
 * entrada.
 * Inclui validações de leitura segura (inteiros e doubles) e validações de
 * formato
 * para telefone, NIF e email.
 *
 * @author Santiago
 * @version 1.0
 */
public class Validacoes {

    /**
     * Lê um número inteiro a partir da entrada do utilizador, repetindo a leitura
     * até que um valor válido seja introduzido.
     *
     * @param ler objeto {@link Scanner} para leitura da entrada
     * @return o valor inteiro lido com sucesso
     */
    public static int lerInteiro(Scanner ler) {
        while (true) {
            try {
                int valor = ler.nextInt();
                ler.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor introduza um número.");
                ler.nextLine();
            }
        }
    }

    /**
     * Lê um número decimal (double) a partir da entrada do utilizador, repetindo a
     * leitura
     * até que um valor válido seja introduzido.
     *
     * @param ler objeto {@link Scanner} para leitura da entrada
     * @return o valor double lido com sucesso
     */
    public static double lerDouble(Scanner ler) {
        while (true) {
            try {
                double valor = ler.nextDouble();
                ler.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor introduza um número válido.");
                ler.nextLine();
            }
        }
    }

    /**
     * Valida um número de telefone português.
     * O telefone deve ter exatamente 9 dígitos e começar por 2, 3 ou 9.
     *
     * @param telefone número de telefone a validar
     * @return {@code true} se o telefone for válido, {@code false} caso contrário
     */
    public static boolean telefoneValido(String telefone) {
        if (telefone == null || telefone.length() != 9) {
            return false;
        }

        if (!telefone.startsWith("2") && !telefone.startsWith("3") && !telefone.startsWith("9")) {
            return false;
        }

        int i = 0;
        while (i < telefone.length()) {
            if (!Character.isDigit(telefone.charAt(i))) {
                return false;
            }
            i++;
        }
        return true;
    }

    /**
     * Valida um Número de Identificação Fiscal (NIF) português.
     * O NIF deve ter exatamente 9 dígitos numéricos.
     *
     * @param nif NIF a validar
     * @return {@code true} se o NIF for válido, {@code false} caso contrário
     */
    public static boolean nifValido(String nif) {
        if (nif == null || nif.length() != 9) {
            return false;
        }

        int j = 0;
        while (j < nif.length()) {
            if (!Character.isDigit(nif.charAt(j))) {
                return false;
            }
            j++;
        }
        return true;
    }

    /**
     * Valida um endereço de email.
     * Verifica se o email segue o formato {@code designacao@entidade.dominio}.
     *
     * @param email endereço de email a validar
     * @return {@code true} se o email for válido, {@code false} caso contrário
     */
    public static boolean emailValido(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /**
     * Tenta ler uma data em vários formatos comuns e converte-a para o formato da
     * BD (yyyy-MM-dd).
     * Usa um ciclo while para percorrer os formatos.
     * 
     * @param dataInserida A string que o utilizador escreveu
     * @return A data no formato "yyyy-MM-dd", ou null se o formato não for
     *         reconhecido.
     */
    public static String normalizarData(String dataInserida) {
        if (dataInserida == null || dataInserida.isEmpty()) {
            return null;
        }

        String[] formatosAceites = {
                "dd/MM/yyyy",
                "dd-MM-yyyy",
                "yyyy-MM-dd",
                "dd.MM.yyyy"
        };

        int i = 0;
        while (i < formatosAceites.length) {
            try {
                java.time.format.DateTimeFormatter formatterEntrada = java.time.format.DateTimeFormatter
                        .ofPattern(formatosAceites[i]);
                java.time.LocalDate dataConvertida = java.time.LocalDate.parse(dataInserida, formatterEntrada);

                java.time.format.DateTimeFormatter formatterBD = java.time.format.DateTimeFormatter
                        .ofPattern("yyyy-MM-dd");
                return dataConvertida.format(formatterBD);

            } catch (java.time.format.DateTimeParseException e) {
            }
            i++;
        }

        return null;
    }
}
