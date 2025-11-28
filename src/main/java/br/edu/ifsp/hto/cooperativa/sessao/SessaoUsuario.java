package br.edu.ifsp.hto.cooperativa.sessao;

public class SessaoUsuario {

    private static Long associadoId;
    private static String tipo; // "produtor" ou "associacao"

    public static void setAssociadoId(Long id) {
        associadoId = id;
    }

    public static Long getAssociadoId() {
        return associadoId;
    }

    public static void setTipo(String t) {
        tipo = t;
    }

    public static String getTipo() {
        return tipo;
    }
}
