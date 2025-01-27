package org.kata.data;

import org.kata.Enum.OperationTypeEnum;

import java.time.LocalDate;

public record Operation(LocalDate date, OperationTypeEnum type, double amount) {}
