package br.com.mrit.pessoas.application.util;

public class ApplicationUtil {

    private static final int[] PESO_CPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean isValidCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11) || cpf.matches("^(0{11}|1{11}|2{11}|3{11}|4{11}|5{11}|6{11}|7{11}|9{11}|9{11})$")) return false;

        Integer digito1 = calcularDigito(cpf.substring(0, 9), PESO_CPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, PESO_CPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static String applyMaskCPF(String cpf) {
        return String.format("%s.%s.%s-%s", cpf.substring(0,3), cpf.substring(3,6), cpf.substring(6,9), cpf.substring(9));
    }
}