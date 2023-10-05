package digitalhands.az.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@NamedQuery(name = "Contact.getAllContacts",
        query = "select new digitalhands.az.wrapper.ContactWrapper(c.id,c.name) from Contact c")

@Entity
@Data
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContactInformation> contactInformations;

}