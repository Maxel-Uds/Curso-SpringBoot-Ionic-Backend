package com.maxel.cursomc.domain.enums;

public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private Integer cod;
    private String descricao;

    private EstadoPagamento(Integer cod, String descricao) {
        this.descricao = descricao;
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public static EstadoPagamento toEnum(Integer cod) {
        if(cod == null) {
            return null;
        }

        for(EstadoPagamento x : EstadoPagamento.values()) {
            if(cod.equals(x.getCod())) {
                return  x;
            }
        }

        throw  new IllegalArgumentException("ID inválido: " + cod);
    }
}
