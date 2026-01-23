package dao.interfaceDao;

public interface IAdminDAO {

    boolean checkExistUser(String username);

    String getPasswordByUsername(String username);

    String getRoleByUsername(String username);
}
