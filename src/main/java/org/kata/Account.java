package org.kata;

import org.kata.Enum.StatementTypeEnum;
import org.kata.data.Statement;
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
     * Liste des statements effectuez sur le compte
     */
    private List<Statement> statementList;

    public Account() {
        this.balance = 0;
        this.statementList = new ArrayList<>();
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
        this.statementList.add(new Statement(LocalDate.now(), StatementTypeEnum.DEPOSIT, amount));
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
        this.statementList.add(new Statement(LocalDate.now(), StatementTypeEnum.WITHDRAWAL, amount));
    }

    /**
     * Renvoie la liste des operations effectué sur le compte
     * @return la liste des operations effectué sur le compte
     */
    public List<Statement> getStatement() {
        return this.statementList;
    }

    /**
     * Renvoie la liste des operation sous forme de chaine de caractere
     * @return la liste des operation sous forme de chaine de caractere
     */
    public List<String> printStatement() {
        List<String> statementList = new ArrayList<>();
        double currentbalance = 0;
        for (Statement statement : this.statementList) {
            if (StatementTypeEnum.DEPOSIT.equals(statement.type())) currentbalance += statement.amount();
            else currentbalance -= statement.amount();
            statementList.add(
                    statement.date() + ": montant de " + statement.amount() + (StatementTypeEnum.DEPOSIT.equals(statement.type()) ? " déposé sur le" : " retiré du") + " compte, le nouveau montant du compte est de " + currentbalance
            );
        }
        return statementList;
    }
}
