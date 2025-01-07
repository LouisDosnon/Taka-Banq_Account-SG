package org.kata.Enum;

public enum OperationTypeEnum {
    /**
     * Depot sur le compte
     */
    DEPOSIT("deposit"),
    /**
     * Retrait du compte
     */
    WITHDRAWAL("withdrawal");

    private String type;


    OperationTypeEnum(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
