package ha.ecz.com.subscriberpanel.Models;

import java.util.ArrayList;

public class ApplicationUser {
    public int UserId;
    public String FirstName;
    public String LastName;
    public int RoleId;
    public int SubscriberId;
    public String Role;
    public String AuthToken;

    public class Menu
    {
        public int MenuID;
        public String MenuText;
        public String IconCode;
        public String IconURL;
    }
    public ArrayList<Menu> Menus = new ArrayList<Menu>();

    public class MenuAction
    {
        public int MenuActionID;
        public int MenuID;
        public int ActionID;
        public String Action;
    }
    public ArrayList<MenuAction> MenuActions = new ArrayList<MenuAction>();

    @Override
    public String toString() {
        return "ApplicationUser{" +
                "UserId=" + UserId +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", RoleId=" + RoleId +
                ", SubscriberId=" + SubscriberId +
                ", Role='" + Role + '\'' +
                ", AuthToken='" + AuthToken + '\'' +
                ", Menus=" + Menus +
                ", MenuActions=" + MenuActions +
                '}';
    }
}
