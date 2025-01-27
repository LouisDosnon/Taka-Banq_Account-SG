package org.kata;

import org.kata.Enum.OperationTypeEnum;
import org.kata.data.Operation;
import org.kata.exception.AmountTooHighException;
import org.kata.exception.NegativeAmountException;
import org.kata.exception.NotEnoughMoneyException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account {

    /**
     * Montant présent sur le compte
     */
    private double balance;
    /**
     * Liste des opérations effectuez sur le compte
     */
    private List<Operation> operationList;

    public Account() {
        this.balance = 0;
        this.operationList = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Permet de déposer de l'argent sur le compte.<br/>
     * <br/>
     * si le montant est négatif, une érreur NegativeAmountException est renvoyé.<br/>
     * si la balance du compte est plus grande que Double.MAX_VALUE moins le montant ajouter(balance du compte + montant ajour > Double.MAX_VALUE), une érreur AmountTooHighException est renvoyé.<br/>
     * @param amount montant déposer
     */
    public void deposit(double amount) {
        if (amount < 0) throw new NegativeAmountException();
        if (this.balance > Double.MAX_VALUE - amount) throw new AmountTooHighException();

        this.balance += amount;
        this.operationList.add(new Operation(LocalDate.now(), OperationTypeEnum.DEPOSIT, amount));
    }

    /**
     * Permet de retirer de l'argent du compte.<br/>
     * <br/>
     * si le montant est negatif, une érreur NagetiveAmountException est renvoyé.<br/>
     * Si le montant retiré est supérieur au montant présent sur le compte, une erreur NotEnoughtException est renvoyé.<br/>
     * Sinon l'argent est retiré du compte.
     *
     * @param amount montant a retirer
     */
    public void withdrawal(double amount) {
        if (amount < 0) throw new NegativeAmountException();
        if (amount > this.balance) throw new NotEnoughMoneyException();

        this.balance -= amount;
        this.operationList.add(new Operation(LocalDate.now(), OperationTypeEnum.WITHDRAWAL, amount));
    }

    /**
     * Renvoie la liste des operations effectué sur le compte
     * @return la liste des operations effectué sur le compte
     */
    public List<Operation> getStatement() {
        return this.operationList;
    }

    /**
     * Renvoie la liste des operation sous forme de chaine de caractere
     * @return la liste des operation sous forme de chaine de caractere
     */
    public List<String> printStatement() {
        List<String> statementList = new ArrayList<>();
        double currentbalance = 0;
        for (Operation operation: this.operationList) {
            if (OperationTypeEnum.DEPOSIT.equals(operation.type())) currentbalance += operation.amount();
            else currentbalance -= operation.amount();
            statementList.add(
                    operation.date() + ": montant de " + operation.amount() + (OperationTypeEnum.DEPOSIT.equals(operation.type()) ? " déposé sur le" : " retiré du") + " compte, le nouveau montant du compte est de " + currentbalance
            );
        }
        return statementList;
    }
}
