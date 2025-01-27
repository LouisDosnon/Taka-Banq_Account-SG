package org.kata.data;

import org.kata.Enum.StatementTypeEnum;

import java.time.LocalDate;

public record Statement(LocalDate date, StatementTypeEnum type, double amount) {}
