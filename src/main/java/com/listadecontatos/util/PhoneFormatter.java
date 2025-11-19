package com.listadecontatos.util;

/**
 * Classe utilitária final para formatação de números de telefone.
 * 'final' impede que a classe seja estendida.
 * Contém apenas métodos estáticos e não pode ser instanciada.
 */
public final class PhoneFormatter {

    /**
     * Construtor privado para prevenir a instanciação da classe.
     * Classes utilitárias com apenas métodos estáticos não devem ser instanciadas.
     */
    private PhoneFormatter() {}

    /**
     * Formata uma string de entrada em um formato de número de telefone brasileiro.
     * A formatação é aplicada dinamicamente à medida que os dígitos são inseridos.
     * O formato final é (XX) XXXXX-XXXX ou (XX) XXXX-XXXX.
     *
     * @param input A string de entrada, que pode conter dígitos e outros caracteres.
     * @return Uma string formatada ou uma string vazia se a entrada for nula ou vazia.
     */
    public static String format(String input) {
        // Retorna uma string vazia para entrada nula para evitar NullPointerException.
        if (input == null) return "";
        
        // Remove todos os caracteres que não são dígitos.
        String digits = input.replaceAll("\\D", "");
        
        // Se não houver dígitos, retorna uma string vazia.
        if (digits.isEmpty()) return "";

        // Limita o número de dígitos a 11 (2 para DDD + 9 para o número).
        if (digits.length() > 11) {
            digits = digits.substring(0, 11);
        }

        // Se houver até 2 dígitos, formata como o início do DDD: (XX
        if (digits.length() <= 2) {
            return "(" + digits;
        }

        // Separa o DDD do resto do número.
        String ddd = digits.substring(0, 2);
        String rest = digits.substring(2);

        // Se o resto do número tiver até 4 dígitos, formata como: (XX) XXXX
        if (rest.length() <= 4) {
            return "(" + ddd + ") " + rest;
        }

        // Para números mais longos, separa o prefixo do sufixo.
        // O sufixo são sempre os últimos 4 dígitos.
        String prefix = rest.substring(0, rest.length() - 4);
        String suffix = rest.substring(rest.length() - 4);
        
        // Monta o formato final: (XX) XXXXX-XXXX
        return "(" + ddd + ") " + prefix + "-" + suffix;
    }
}
