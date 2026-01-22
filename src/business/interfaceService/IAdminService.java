package business.interfaceService;


public interface IAdminService {

    boolean loginAdmin(String username, String password);


    boolean checkExistAdmin(String username);

    String getPasswordByUsername(String username);


    String getRoleByUsername(String username);
}
