package nl.ruudclaassen.jfall3.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique=true)
    private String code;

    @ManyToOne
    private Promotion promotion;

    public Code() {
    }

    public Code(String code, Promotion promotion) {
        this.code = code;
        this.promotion = promotion;
    }

    public Code(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
}
