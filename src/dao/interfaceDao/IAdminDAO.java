package dao.interfaceDao;

public interface IAdminDAO {
    boolean loginAdmin(String username, String password);

    boolean checkExistAdmin(String username);

    String getPasswordByUsername(String username);

    String getRoleByUsername(String username);
}
