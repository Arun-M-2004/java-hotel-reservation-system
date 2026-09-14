package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    // Regex: name@domain.com
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$";

    public Customer(String firstName, String lastName, String email) {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException("Invalid email format. Use name@domain.com");
        }
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
    }

    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName;  }
    public String getEmail()     { return email;     }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + " | Email: " + email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer c = (Customer) obj;
        return Objects.equals(email, c.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
