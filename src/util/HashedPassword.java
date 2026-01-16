package util;

import org.mindrot.jbcrypt.BCrypt;

public class HashedPassword {
    static void main() {
        String rawPassword = "123456";

        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(10)); //Tạo chuỗi băm ngẫu nhiên với độ phức tạp là 10 trước khi băm
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
