package company.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "accounts",schema = "public")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "account")
    private AccountStatus accountStatus;

    public Account(AccountStatus accountStatus, long id) {

        this.accountStatus = accountStatus;
        this.id = id;
    }

    public Account() {
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public long getDevId() {
        return id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account(AccountStatus accountStatus) {

        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountStatus=" + accountStatus +
                '}';
    }
}