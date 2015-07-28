package com.cyou.mobopick.bus;

import com.cyou.mobopick.domain.Group;

/**
 * Created by chengfei on 14/11/18.
 */
public class AddGroupEvent{
    private Group group;

    public AddGroupEvent(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }
}
