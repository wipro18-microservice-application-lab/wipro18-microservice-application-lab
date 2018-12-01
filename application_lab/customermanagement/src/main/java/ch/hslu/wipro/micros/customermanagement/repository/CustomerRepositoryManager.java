package ch.hslu.wipro.micros.customermanagement.repository;

import ch.hslu.wipro.micros.customermanagement.dto.CustomerDto;
import ch.hslu.wipro.micros.customermanagement.dto.CustomerDtoBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class CustomerRepositoryManager {
    private final Random rand = new Random();
    private final CustomerRepository customerRepository;
    private final List<String> nameList = Arrays.asList(
            "Alan Meile",
            "Cyrill Jauner",
            "Yannis Baumann",
            "Dominik Hirzel",
            "Marco Weber",
            "Elias Schmidt",
            "Genny Maurer",
            "Daniela Keller"
    );
    private final List<String> addressList = Arrays.asList(
            "517 Green Gate Lane",
            "4514 Jerry Dove Drive",
            "521 Trouser Leg Road",
            "997 Randolph Street",
            "2255 Raoul Wallenberg Place",
            "3187 Beechwood Drive",
            "1564 Jewell Road",
            "593 Memory Lane"
    );

    public CustomerRepositoryManager(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void generateCustomers(int amount) {

        int i = 0;
        while (i < amount) {
            String name = pickRandomNameFromList(nameList);
            String address = pickRandomNameFromList(addressList);
            String email = Stream.of(name.split(" "))
                    .reduce((p, n) -> String.format("%s.%s@gmail.com", p.toLowerCase(), n.toLowerCase()))
                    .orElse("none");

            CustomerDto customerDto = new CustomerDtoBuilder()
                    .atFullName(name)
                    .atAddress(address)
                    .atEmail(email)
                    .build();

            customerRepository.create(customerDto);
            i++;
        }
    }

    private String pickRandomNameFromList(List<String> list) {
        return list.get(rand.nextInt(list.size()));
    }
}
