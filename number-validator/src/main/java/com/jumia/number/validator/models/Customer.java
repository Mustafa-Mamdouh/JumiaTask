package com.jumia.number.validator.models;

import com.jumia.number.validator.enums.NumberState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customer")
@SqlResultSetMapping(name = "CustomerMapping",
        classes = {
                @ConstructorResult(
                        targetClass = Customer.class,
                        columns = {
                                @ColumnResult(name = "id", type = Integer.class),
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "phone", type = String.class),
                                @ColumnResult(name = "country", type = String.class),
                                @ColumnResult(name = "state", type = String.class)
                        })
        })
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer id;
    String name;
    String phone;
    @Transient
    String country;
    @Transient
    NumberState state;

    public Customer(Integer id, String name, String phone, String country, String state) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.country = country;
        this.state = NumberState.valueOf(state);
    }
}
