package org.kata;

import org.kata.Enum.OperationTypeEnum;
import org.kata.data.Operation;
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
     * Permet de déposer de l'argent sur le compte
     * @param amount montant déposer
     */
    public void deposit(double amount){
        this.balance += amount;
        this.operationList.add(new Operation(LocalDate.now(), OperationTypeEnum.DEPOSIT, amount, this.balance));
    }

    /**
     * Permet de retirer de l'argent du compte.<br/>
     * <br/>
     * Si le montant retiré est supérieur au montant présent sur le compte, une erreur NotEnoughtException est renvoyé.<br/>
     * Sinon l'argent est retiré du compte.
     *
     * @param amount montant a retirer
     */
    public void withdrawal(double amount) {
        if (amount > this.balance) {
            throw new NotEnoughMoneyException();
        }
        this.balance -= amount;
        this.operationList.add(new Operation(LocalDate.now(), OperationTypeEnum.WITHDRAWAL, amount, this.balance));
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
        for (Operation operation: this.operationList) {
            statementList.add(operation.toString());
        }
        return statementList;
    }
}
