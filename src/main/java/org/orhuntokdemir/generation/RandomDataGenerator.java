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
        return (short) (random.nextInt(Short.MAX_VALUE - Short.MIN_VALUE + 1) + Short.MIN_VALUE);
    }

    public float generateReal() {
        return random.nextFloat();
    }

    public double generateDouble() {
        return random.nextDouble();
    }

    public java.math.BigDecimal generateNumeric(int precision, int scale) {
        int integerPart = random.nextInt((int) Math.pow(10, precision - scale));
        return new java.math.BigDecimal(integerPart).setScale(scale, java.math.RoundingMode.HALF_UP);
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
        return java.util.UUID.randomUUID().toString();
    }

    // JSON (as string)
    public String generateJson() {
        return "{\"name\": \"" + generateName() + "\", \"email\": \"" + generateEmail() + "\"}";
    }

    // BYTEA (byte array)
    public byte[] generateBytea() {
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }

    // MONEY
    public String generateMoney() {
        return String.format("%.2f", random.nextDouble() * 10000);
    }

    // Timestamp with timezone
    public LocalDateTime generateTimestampTz() {
        return LocalDateTime.now().minusDays(random.nextInt(365 * 10)).minusHours(random.nextInt(24));
    }

    // Time with timezone
    public LocalTime generateTimeTz() {
        return LocalTime.of(random.nextInt(24), random.nextInt(60), random.nextInt(60));
    }

    // INTERVAL (as string in ISO 8601 format)
    public String generateInterval() {
        return String.format("PT%dH%dM%dS", random.nextInt(24), random.nextInt(60), random.nextInt(60));
    }

    // POINT (as string for PostgreSQL)
    public String generatePoint() {
        return String.format("(%.2f,%.2f)", random.nextDouble() * 100, random.nextDouble() * 100);
    }

    // LINE (as string for PostgreSQL)
    public String generateLine() {
        return String.format("{%.2f,%.2f,%.2f}", random.nextDouble() * 100, random.nextDouble() * 100, random.nextDouble() * 100);
    }

    // LSEG (line segment as string)
    public String generateLseg() {
        return String.format("((%.2f,%.2f),(%.2f,%.2f))", random.nextDouble() * 100, random.nextDouble() * 100,
                random.nextDouble() * 100, random.nextDouble() * 100);
    }

    // BOX (as string)
    public String generateBox() {
        double x1 = random.nextDouble() * 100;
        double y1 = random.nextDouble() * 100;
        double x2 = x1 + random.nextDouble() * 50;
        double y2 = y1 + random.nextDouble() * 50;
        return String.format("((%.2f,%.2f),(%.2f,%.2f))", x1, y1, x2, y2);
    }

    // PATH (as string)
    public String generatePath() {
        StringBuilder path = new StringBuilder("(");
        for (int i = 0; i < 3; i++) {
            if (i > 0) path.append(",");
            path.append(String.format("(%.2f,%.2f)", random.nextDouble() * 100, random.nextDouble() * 100));
        }
        path.append(")");
        return path.toString();
    }

    // POLYGON (as string)
    public String generatePolygon() {
        StringBuilder polygon = new StringBuilder("(");
        for (int i = 0; i < 4; i++) {
            if (i > 0) polygon.append(",");
            polygon.append(String.format("(%.2f,%.2f)", random.nextDouble() * 100, random.nextDouble() * 100));
        }
        polygon.append(")");
        return polygon.toString();
    }

    // CIRCLE (as string)
    public String generateCircle() {
        return String.format("<(%.2f,%.2f),%.2f>", random.nextDouble() * 100, random.nextDouble() * 100, 
                random.nextDouble() * 50);
    }

    // CIDR (IP CIDR address)
    public String generateCidr() {
        return String.format("%d.%d.%d.%d/24", random.nextInt(256), random.nextInt(256), 
                random.nextInt(256), random.nextInt(256));
    }

    // INET (IP address)
    public String generateInet() {
        return String.format("%d.%d.%d.%d", random.nextInt(256), random.nextInt(256), 
                random.nextInt(256), random.nextInt(256));
    }

    // MACADDR (MAC address)
    public String generateMacaddr() {
        return String.format("%02x:%02x:%02x:%02x:%02x:%02x", random.nextInt(256), random.nextInt(256), 
                random.nextInt(256), random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    // BIT (bit string)
    public String generateBit(int length) {
        StringBuilder bit = new StringBuilder("B'");
        for (int i = 0; i < length; i++) {
            bit.append(random.nextBoolean() ? "1" : "0");
        }
        bit.append("'");
        return bit.toString();
    }

    // VARBIT (variable bit string)
    public String generateVarbit(int maxLength) {
        StringBuilder varbit = new StringBuilder("B'");
        int length = random.nextInt(maxLength - 1) + 1;
        for (int i = 0; i < length; i++) {
            varbit.append(random.nextBoolean() ? "1" : "0");
        }
        varbit.append("'");
        return varbit.toString();
    }

    // JSONB (JSON as string, same as JSON for our purposes)
    public String generateJsonb() {
        return generateJson();
    }

    // Array of integers (PostgreSQL array format)
    public String generateIntArray(int size) {
        StringBuilder array = new StringBuilder("{");
        for (int i = 0; i < size; i++) {
            if (i > 0) array.append(",");
            array.append(random.nextInt(1000));
        }
        array.append("}");
        return array.toString();
    }

    // XML (as string)
    public String generateXml() {
        return String.format("<record><name>%s</name><email>%s</email></record>", generateName(), generateEmail());
    }

    // ENUM value (gender)
    public String generateGender() {
        return random.nextBoolean() ? "male" : "female";
    }
}