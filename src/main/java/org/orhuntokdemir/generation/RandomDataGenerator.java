package org.orhuntokdemir.generation;

import net.datafaker.Faker;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

public class RandomDataGenerator {
    private final Faker faker;
    private final Random random;

    public RandomDataGenerator() {
        this.faker = new Faker();
        this.random = new Random();
    }

    // String types
    public String generateVarchar(int maxLength) {
        String text = faker.lorem().word();
        return text.substring(0, Math.min(text.length(), maxLength));
    }
    // Generate a random Turkish Identification Number (TCKN)
    public String generateTCKN() {
        StringBuilder tckn = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            tckn.append(random.nextInt(10));
        }
        return tckn.toString();
    }
    public String generateText() {
        return faker.lorem().sentence();
    }

    public String generateChar(int length) {
        String text = faker.lorem().word();
        StringBuilder sb = new StringBuilder(text);
        while (sb.length() < length) {
            sb.append(faker.lorem().word());
        }
        return sb.toString().substring(0, length);
    }

    // Numeric types
    public int generateInt() {
        return random.nextInt();
    }

    public long generateBigint() {
        return random.nextLong();
    }

    public short generateSmallint() {
        return (short) random.nextInt(Short.MAX_VALUE + 1);
    }

    public float generateReal() {
        return random.nextFloat();
    }

    public double generateDouble() {
        return random.nextDouble();
    }

    public java.math.BigDecimal generateNumeric(int precision, int scale) {
        return new java.math.BigDecimal(random.nextDouble()).setScale(scale, java.math.RoundingMode.HALF_UP);
    }

    // Boolean
    public boolean generateBoolean() {
        return random.nextBoolean();
    }

    // Date/Time types
    public LocalDate generateDate() {
        return LocalDate.now().minusDays(random.nextInt(365 * 10));
    }

    public LocalTime generateTime() {
        return LocalTime.of(random.nextInt(24), random.nextInt(60), random.nextInt(60));
    }

    public LocalDateTime generateTimestamp() {
        return LocalDateTime.now().minusDays(random.nextInt(365 * 10)).minusHours(random.nextInt(24));
    }

    // Name (commonly used)
    public String generateName() {
        return faker.name().fullName();
    }

    public String generateEmail() {
        return faker.internet().emailAddress();
    }

    public String generatePhone() {
        return faker.phoneNumber().phoneNumber();
    }

    // UUID
    public String generateUUID() {
        return faker.idNumber().valid();
    }

    // JSON (as string)
    public String generateJson() {
        return "{\"name\": \"" + generateName() + "\", \"email\": \"" + generateEmail() + "\"}";
    }
}