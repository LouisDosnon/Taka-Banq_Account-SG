import jdk.jfr.Label;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.kata.Account;
import org.kata.Enum.StatementTypeEnum;
import org.kata.data.Statement;
import org.kata.exception.AmountTooHighException;
import org.kata.exception.NegativeAmountException;
import org.kata.exception.NotEnoughMoneyException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AccountTest {
    @Test
    @Label("initialisation of Account")
    public void initAccountTest() {
        Account account = new Account();

        // le compte est initialisé avec un montant de 0.0.
        assertEquals(0.0, account.getBalance(), 0);
        // aucune opération n'a été effectué sur le compte.
        assertEquals(new ArrayList<Statement>(), account.getStatement());
    }

    @Test
    @Label("deposit of 500.0 on Account")
    public void depositAccountTest() {
        Account account = new Account();
        account.deposit(500.0);

        // le montant sur le compte doit être égale à ce qui a été deposé sur le compte soit 500.0.
        assertEquals(500.0, account.getBalance(), 0);

        List<Statement> expectedStatement = new ArrayList<>();
        expectedStatement.add(new Statement(LocalDate.now(), StatementTypeEnum.DEPOSIT, 500.0));

        // une seule opération de depot doit être présente.
        assertEquals(1, account.getStatement().size());
        assertEquals(expectedStatement.get(0), account.getStatement().get(0));
    }

    @Test
    @Label("deposit of Double.MAX_VALUE on Account")
    public void maxDepositAccountTest() {
        Account account = new Account();
        account.deposit(Double.MAX_VALUE);

        // le montant sur le compte doit être égale à ce qui a été deposé sur le compte soit Double.MAX_VALUE.
        assertEquals(Double.MAX_VALUE, account.getBalance(), 0);

        List<Statement> expectedStatement = new ArrayList<>();
        expectedStatement.add(new Statement(LocalDate.now(), StatementTypeEnum.DEPOSIT, Double.MAX_VALUE));

        // une seule opération de depot doit être présente.
        assertEquals(1, account.getStatement().size());
        assertEquals(expectedStatement.get(0), account.getStatement().get(0));
    }

    @Test
    @Label("deposit of -1.0 on Account should failed")
    public void invalidNegativeDepositAccountTest() {
        Account account = new Account();

        // le montant sur le compte étant inférieur à 0, une erreur doit être renvoyé.
        NegativeAmountException exception = assertThrows(NegativeAmountException.class, () -> account.deposit(-1.0));
        assertEquals("Amount cannot be negative.", exception.getMessage());

        // le compte ne doit pas être modifié
        assertEquals(0.0, account.getBalance(), 0.0);
        // une seule opération de depot doit être présente.
        assertEquals(0.0, account.getStatement().size(), 0.0);
        assertEquals(new ArrayList<Statement>(), account.getStatement());
    }

    @Test
    @Label("deposit of Double.MAX_VALUE on Account should failed")
    public void invalidAmountTooHighDepositAccountTest() {
        Account account = new Account();
        account.deposit(1.0);

        // le montant sur le compte étant supérieur à Double.MAX_VALUE, une erreur doit être renvoyé.
        AmountTooHighException exception = assertThrows(AmountTooHighException.class, () -> account.deposit(Double.MAX_VALUE));
        assertEquals("Amount is too high.", exception.getMessage());

        // le compte ne doit pas être modifier
        assertEquals(1.0, account.getBalance(), 0.0);

        // une seule opération de depot doit être présente
        List<Statement> expectedStatement = new ArrayList<>();
        expectedStatement.add(new Statement(LocalDate.now(), StatementTypeEnum.DEPOSIT, 1.0));

        assertEquals(1.0, account.getStatement().size(), 0.0);
        assertEquals(expectedStatement.get(0), account.getStatement().get(0));
    }

    @Test
    @Label("two deposit of 500.0 and 250.0 on Account")
    public void twoDepositAccountTest() {
        Account account = new Account();
        account.deposit(500.0);
        account.deposit(250.0);

        // le montant sur le compte doit être égale à ce qui a été deposé sur le compte soit 750.0(500.0+250.0).
        assertEquals(750.0, account.getBalance(), 0);

        List<Statement> expectedStatement = new ArrayList<>();
        expectedStatement.add(new Statement(LocalDate.now(), StatementTypeEnum.DEPOSIT, 500.0));
        expectedStatement.add(new Statement(LocalDate.now(), StatementTypeEnum.DEPOSIT, 250.0));

        // une seule opération de depot doit être présente.
        assertEquals(2, account.getStatement().size());
        assertEquals(expectedStatement.get(0), account.getStatement().get(0));
        assertEquals(expectedStatement.get(1), account.getStatement().get(1));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "250.0:250.0",  // retrait d'une partie du montant present sur le compte
            "500.0:0.0"     // retrait de tous l'argent present sur le compte
    }, delimiter = ':')
    @Label("deposit of 500.0 and valid withdraw on Account")
    public void validWithdrawalAccountTest(double amount, double expectedBalance) {
        Account account = new Account();
        account.deposit(500.0);
        account.withdrawal(amount);

        // le montant doit être égal montant attendue.
        assertEquals(expectedBalance, account.getBalance(), 0);

        // une opération de depot et une opération de retrait doivent être présents.
        List<Statement> expectedStatement = new ArrayList<>();
        expectedStatement.add(new Statement(LocalDate.now(), StatementTypeEnum.DEPOSIT, 500.0));
        expectedStatement.add(new Statement(LocalDate.now(), StatementTypeEnum.WITHDRAWAL, amount));

        assertEquals(2, account.getStatement().size());
        assertEquals(expectedStatement.get(0), account.getStatement().get(0));
        assertEquals(expectedStatement.get(1), account.getStatement().get(1));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "500.01",
            "750.0"
    })
    @Label("deposit of 500.0 and invalid withdraw on Account")
    public void invalidWithdrawalAccountTest(double amount) {
        Account account = new Account();
        account.deposit(500.0);

        // le montant retirer est supérieur à montant present sur le compte, une exception NotEnoughtException doit être renvoyé.
        NotEnoughMoneyException exception = assertThrows(NotEnoughMoneyException.class, () -> account.withdrawal(amount));
        assertEquals("You do not have the amount necessary for this operation.", exception.getMessage());

        List<Statement> expectedStatement = new ArrayList<>();
        expectedStatement.add(new Statement(LocalDate.now(), StatementTypeEnum.DEPOSIT, 500.0));

        // l'argent ne doit pas être retiré du compte.
        assertEquals(500.0, account.getBalance(), 0);

        // seul l'opération de depot doit être présent.
        assertEquals(1, account.getStatement().size());
        assertEquals(expectedStatement.get(0), account.getStatement().get(0));
    }

    @Test
    @Label("deposit of -1.0 and invalid withdraw on Account")
    public void invalidNegativeWithdrawalAccountTest() {
        Account account = new Account();
        account.deposit(500.0);

        // le montant retirer est supérieur à montant present sur le compte, une exception NotEnoughtException doit être renvoyé.
        NegativeAmountException exception = assertThrows(NegativeAmountException.class, () -> account.withdrawal(-1));
        assertEquals("Amount cannot be negative.", exception.getMessage());

        List<Statement> expectedStatement = new ArrayList<>();
        expectedStatement.add(new Statement(LocalDate.now(), StatementTypeEnum.DEPOSIT, 500.0));

        // l'argent ne doit pas être retiré du compte.
        assertEquals(500.0, account.getBalance(), 0);

        // seul l'opération de depot doit être présent.
        assertEquals(1, account.getStatement().size());
        assertEquals(expectedStatement.get(0), account.getStatement().get(0));
    }

    @Test
    @Label("print all the statement of Account")
    public void printStatementAccountTest() {
        Account account = new Account();
        account.deposit(500.0);
        account.withdrawal(250.0);

        // une opération de depot et une opération de retrait doivent être présents.
        List<String> expected = new ArrayList<>();
        expected.add(LocalDate.now() + ": montant de 500.0 déposé sur le compte, le nouveau montant du compte est de 500.0");
        expected.add(LocalDate.now() + ": montant de 250.0 retiré du compte, le nouveau montant du compte est de 250.0");

        List<String> statement = account.printStatement();
        assertEquals(2, statement.size());
        assertEquals(expected.get(0), statement.get(0));
        assertEquals(expected.get(1), statement.get(1));
    }
}
