package digitalhands.az.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@NamedQuery(name = "Graduate.getAllGraduate",
        query = "select new digitalhands.az.wrapper.GraduateWrapper" +
                "(g.name,g.surname,g.content) from Graduate g")

@Entity
@Setter
@Getter
@Table(name = "graduate")
public class Graduate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "content")
    private String content;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "experience_id", nullable = false)
    @JsonIgnore
    private Experience experience;

    @Override
    public String toString() {
        return "Graduate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", content='" + content + '\'' +
                ", imageData=" + Arrays.toString(imageData) +
                '}';
    }

}