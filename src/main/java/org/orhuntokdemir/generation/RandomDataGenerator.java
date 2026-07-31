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
    // Generate a random Turkish Identification Number (TCKN). All TCKNS are randomly generated and do not correspond to real individuals.
    public String generateFakeTCKN() {
        StringBuilder tckn = new StringBuilder(11);

        int[] digit = new int[11];
        int sumOdd=0;
        int sumEven=0;

        //First digit cannot be 0, so we generate a random number between 1 and 9 for the first digit
        digit[0]= random.nextInt(9) + 1; // First digit cannot be 0
        tckn.append(digit[0]);

        //Generates the next 8 digits randomly and append them
        for(int i=1;i<9;i++){
            digit[i]= random.nextInt(10);
            tckn.append(digit[i]);
        }

        //Calculates the sum of odd and even indexed digits (0-based index) for the first 9 digits
        for(int i=0;i<9;i++){
            if(i%2==0){
                sumOdd+=digit[i];
            }else{
                sumEven+=digit[i];
            }
        }

        //Calculates the 10th digit based on the TCKN rules, which is calculated based on the first 9 digits
        digit[9] = (7 * sumOdd - sumEven) % 10;

        // If the tenth digit is negative, we add 10 to make it positive
        if(digit[9]<0){
            digit[9] +=10;
        }
        tckn.append(digit[9]);

        //Calculates the 11th digit based on the TCKN rules, which is calculated based on the first 10 digits
        digit[10] = (sumOdd + sumEven + digit[9]) % 10;
        tckn.append(digit[10]);

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

    //ROWID

    // JSON (as string)
    public String generateJson() {
        return "{\"name\": \"" + generateName() + "\", \"email\": \"" + generateEmail() + "\"}";
    }

    // BYTEA (byte array)
    public byte[] generateBytea() {
        byte[] bytes = new byte[20]; // Default length of 20 bytes
        random.nextBytes(bytes);
        return bytes;
    }

    public byte[] generateBytea(int length) {
        byte[] bytes = new byte[length];
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
        return String.format("%d.%d.%d.0/24", random.nextInt(256), random.nextInt(256), 
                random.nextInt(256));
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
        StringBuilder bit = new StringBuilder();
        for (int i = 0; i < length; i++) {
            bit.append(random.nextBoolean() ? "1" : "0");
        }
        return bit.toString();
    }

    // VARBIT (variable bit string)
    public String generateVarbit(int maxLength) {
        StringBuilder varbit = new StringBuilder();
        int length = random.nextInt(maxLength - 1) + 1;
        for (int i = 0; i < length; i++) {
            varbit.append(random.nextBoolean() ? "1" : "0");
        }
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


    // XML
    public String generateXml() {
        return "<root><element>" + faker.lorem().word() + "</element></root>";
    }

    // Oracle ANYDATA-compatible value.
    // Use with SYS.ANYDATA.ConvertVarchar2(?) in the INSERT statement.
    public String generateAnyDataVarchar2() {
        return generateVarchar(100);
    }

    // Oracle ANYTYPE
    public String generateAnyType() {
        return "SYS.ANYTYPE";
    }

    // Oracle ANYDATASET
    public String generateAnyDataSet() {
        return null; // Very complex to generate randomly without specific schema
    }

    // Oracle HTTPURITYPE-compatible URI.
    // Use with HTTPURITYPE(?) in the INSERT statement.
    public String generateHttpUri() {
        return "https://example.com/" + faker.internet().slug();
    }

    // Oracle XDBURIType-compatible URI path.
    // Use with XDBURIType(?) in the INSERT statement.
    public String generateXdbUri() {
        return "/public/" + faker.internet().slug() + ".xml";
    }

    // Oracle DBURIType-compatible URI.
    // Use with DBURIType(?) in the INSERT statement.
    public String generateDbUri() {
        return "/PUBLIC/TEST_DATA/ROW[" + (random.nextInt(1000) + 1) + "]";
    }

    // Oracle Spatial SDO_GEOMETRY-compatible point arguments.
    // Use with SDO_GEOMETRY(2001, 4326, SDO_POINT_TYPE(?, ?, NULL), NULL, NULL).
    public double generateLongitude() {
        return -180 + (360 * random.nextDouble());
    }

    public double generateLatitude() {
        return -90 + (180 * random.nextDouble());
    }

    public String generateSdoGeometry() {
        return String.format("SDO_GEOMETRY(2001, 4326, SDO_POINT_TYPE(%.2f, %.2f, NULL), NULL, NULL)",
                generateLongitude(), generateLatitude());
    }

    public String generateSdoTopoGeometry() {
        return null; // Topo geometry requires existing topology in the DB
    }

    public String generateSdoGeoraster() {
        return null; // GeoRaster is very complex and requires specific setup
    }

    public String generateRowId() {
        // Mocking a ROWID format: AAAR1+AAEAAAAAgAAB
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 18; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // ENUM value (gender)

    // ENUM value (gender)
    public String generateGender() {
        return random.nextBoolean() ? "male" : "female";
    }
}