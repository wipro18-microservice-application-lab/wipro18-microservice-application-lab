package ch.hslu.wipro.micros.customermanagement.repository;

public interface CustomerRepository {

    /**
     * Returns a flagged CustomerOperation containing information about the success of the operation
     * and the requested articleDto.
     *
     * @param id    the id of the article requested.
     * @return      CustomerOperation containing success and articleDto.
     */
    CustomerOperation getCustomerById(long id);
}
