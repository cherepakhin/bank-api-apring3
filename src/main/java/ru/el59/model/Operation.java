package ru.el59.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Перевод со счета на счет
 */
@Entity
@Table(name = "operation")
public class Operation extends AEntity {
    // Дата операции
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ddate = new Date();
    // С какого счета
    private Account srcAccount;
    // На какой
    private Account dstAccount;
    // Сумма
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal amount = new BigDecimal("0.00");
    private String comment = "";

    @Temporal(TemporalType.DATE)
    public Date getDdate() {
        return ddate;
    }

    public void setDdate(Date ddate) {
        this.ddate = ddate;
    }

    @ManyToOne
    @JoinColumn(name = "src_account_id")
    public Account getSrcAccount() {
        return srcAccount;
    }

    public void setSrcAccount(Account srcAccount) {
        this.srcAccount = srcAccount;
    }

    @ManyToOne
    @JoinColumn(name = "dst_account_id")
    public Account getDstAccount() {
        return dstAccount;
    }

    public void setDstAccount(Account dstAccount) {
        this.dstAccount = dstAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", srcAccount=" + srcAccount.getId() +
                ", dstAccount=" + dstAccount.getId() +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;
        Operation that = (Operation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, srcAccount, dstAccount, amount);
    }
}
