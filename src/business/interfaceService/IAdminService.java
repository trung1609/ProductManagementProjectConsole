package business.interfaceService;


public interface IAdminService {


    boolean checkExistUser(String username);

    String getPasswordByUsername(String username);


    String getRoleByUsername(String username);
}
