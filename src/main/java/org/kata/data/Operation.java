package org.kata.data;

import org.kata.Enum.OperationTypeEnum;

import java.time.LocalDate;

public class Operation {
    /**
     * Date de l'opération
     */
    private LocalDate date;
    /**
     * type de l'operation (soit "deposit", soit "withdrawal"
     */
    private OperationTypeEnum type;
    /**
     * Montant de l'opération
     */
    private double amount;
    /**
     * Montant du compte après opération
     */
    private double balance;

    public Operation(LocalDate date, OperationTypeEnum type, double amount, double balance) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public OperationTypeEnum getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            // l'object comparer est le meme que l'object courant
            return true;
        }
        if (!(other instanceof Operation)) {
            // l'object comparer n'est pas une operation
            return false;
        }
        // convertion de other en Operation
        Operation otherOperation = (Operation) other;

        // comparaison des differentes valeur
        return otherOperation.date.equals(this.date)
                && otherOperation.type.equals(this.type)
                && otherOperation.amount == this.amount
                && otherOperation.balance == this.balance;
    }

    @Override
    public String toString() {
        return this.date + ": montant de " + this.amount + (OperationTypeEnum.DEPOSIT.equals(this.type) ? " déposé sur le" : " retiré du") + " compte, le nouveau montant du compte est de " + this.balance;
    }
}
