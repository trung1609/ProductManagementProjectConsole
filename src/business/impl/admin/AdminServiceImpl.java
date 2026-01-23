package business.impl.admin;

import business.interfaceService.IAdminService;
import dao.impl.admin.AdminDAOImpl;
import dao.interfaceDao.IAdminDAO;

public class AdminServiceImpl implements IAdminService {
    private final IAdminDAO adminDAO;

    public AdminServiceImpl() {
        this.adminDAO = new AdminDAOImpl();
    }


    @Override
    public boolean checkExistUser(String username) {

        if (username == null || username.trim().isEmpty()) {
            return false;
        }


        return adminDAO.checkExistUser(username);
    }

    @Override
    public String getPasswordByUsername(String username) {

        if (username == null || username.trim().isEmpty()) {
            return null;
        }


        return adminDAO.getPasswordByUsername(username);
    }

    @Override
    public String getRoleByUsername(String username) {

        if (username == null || username.trim().isEmpty()) {
            return null;
        }


        return adminDAO.getRoleByUsername(username);
    }
}
