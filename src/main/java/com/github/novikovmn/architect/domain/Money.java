package com.github.novikovmn.architect.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Сущность типа "Денежная сумма". Содержит сумму и валюту, в которой представлена сумма. Содержит математические
 * операции для взаимодействия с суммой.
 */
@Data
@Entity
@Table(name = "moneys")
public class Money {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "currency")
    private Currency currency;

    /**
     * Сложение с другим объектом типа "Денежная сумма".
     * @param arg Объект типа "Денежная сумма", с которым требуется сложить текущий объект
     * @return Текущий объект с обновлённой суммой - результатом сложения.
     */
    public Money add(Money arg) { //TODO: сделать с учётом курса валюты
        amount = amount.add(arg.getAmount());
        return this;
    }

    /**
     * Вычитание другого объекта типа "Денежная сумма"
     * @param arg Объект типа "Денежная сумма", который требуется вычесть из текущего объекта
     * @return Текущий объект с обновлённой суммой - результатом вычитания
     */
    public Money substract(Money arg) { //TODO: сделать с учётом курса валюты
        amount = amount.subtract(arg.getAmount());
        return this;
    }

    /**
     * Перемножение на число типа BigDecimal
     * @param arg Объект типа "Денежная сумма", на который требуется перемножить текущий объект
     * @return Текущий объект с обновлённой суммой - результатом перемножения
     */
    public Money multiply(BigDecimal arg) {
        amount = amount.multiply(arg);
        return this;
    }

    /**
     * Перемножение на число типа int. Обёртка над функкцией перемножения на число типа BigDecimal
     * @param arg Множитель
     * @return Текущий объект с обновлённой суммой - результатом перемножения
     */
    public Money multiply(int arg) { return this.multiply(BigDecimal.valueOf(arg)); }

    /**
     * Перемножение на число типа float. Обёртка над функцией перемножения на число типа BigDecimal
     * @param arg Множитель
     * @return Текущий объект с обновлённой суммой - результатом перемножения
     */
    public Money multiply(float arg) { return multiply(BigDecimal.valueOf(arg)); }

    /**
     * Деление на число типа BigDecimal
     * @param arg Делитель
     * @return Текущий объект с обновлённой суммой - результатом деления
     */
    public Money divide(BigDecimal arg) {
        amount = amount.divide(arg, 15, RoundingMode.HALF_UP);
        return this;
    }

    /**
     * Деление на число типа int. Обёртка над функцией деления на число типа BigDecimal
     * @param arg Делитель
     * @return Текущий объект с обновлённой суммой - результатом деления
     */
    public Money divide(int arg) { return this.divide(BigDecimal.valueOf(arg)); }

    /**
     * Деление на число типа float. Обёртка над функцией деления на число типа BigDecimal
     * @param arg Делитель
     * @return Текущий объект с обновлённой суммой - результатом деления
     */
    public Money divide(float arg) { return this.divide(BigDecimal.valueOf(arg)); }

    /**
     * Приведение к определённой валюте
     * @param currency Валюта, к которой требуется привести текущий объект
     * @return Текущий объект с новой валютой и суммой, пересчитанной в соотетствии с курсом новой валюты
     */
    public Money toCurrency(Currency currency) {
        this.multiply(this.currency.getRate()).divide(currency.getRate());
        this.currency = currency;
        return this;
    }

    @Override
    public String toString() {
        return "Money(id="+id+", amount="+amount+", currency="+currency.toString()+")";
    }

}
