package sz.internal.acm.to;

import sz.internal.common.base.to.BaseSearchTO;

public class UserSearchTO extends BaseSearchTO {
    private String username;
    private String username_like;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername_like() {
        return username_like;
    }

    public void setUsername_like(String username_like) {
        this.username_like = username_like;
    }
}


