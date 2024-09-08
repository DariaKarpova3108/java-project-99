package hexlet.code.util;

import hexlet.code.model.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class ModelGenerator {
    private Model<User> userModel;

    @Autowired
    private Faker faker;

    @PostConstruct
    public void generate() {
        userModel = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getFirstName), ()-> faker.name().firstName())
                .supply(Select.field(User::getLastName), () -> faker.name().lastName())
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(User::getPasswordDigest), () -> faker.internet().password(3, 10))
                .toModel();

    }
}
