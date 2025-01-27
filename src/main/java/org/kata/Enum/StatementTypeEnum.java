package org.kata.Enum;

public enum StatementTypeEnum {
    /**
     * Depot sur le compte
     */
    DEPOSIT("deposit"),
    /**
     * Retrait du compte
     */
    WITHDRAWAL("withdrawal");

    private String type;


    StatementTypeEnum(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
