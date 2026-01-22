package util;

import entity.Admin;
import entity.Role;

public class SessionManager {
    private static Admin currentUser = null;

    public static Admin getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Admin admin) {
        currentUser = admin;
    }

    public static Role getCurrentRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }

    public static boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == Role.ADMIN;
    }

    public static boolean isStaff() {
        return currentUser != null && currentUser.getRole() == Role.STAFF;
    }

    public static void clearSession() {
        currentUser = null;
    }
}
