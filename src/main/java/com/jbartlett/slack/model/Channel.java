package com.jbartlett.slack.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by johnbartlett on 18/08/15.
 */
public class Channel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("is_channel")
    @Expose
    private boolean channel;

    @SerializedName("created")
    @Expose
    private Long created;

    @SerializedName("creator")
    @Expose
    private String creator;

    @SerializedName("is_archived")
    @Expose
    private boolean archived;

    @SerializedName("is_general")
    @Expose
    private boolean general;

    @SerializedName("is_member")
    @Expose
    private boolean member;

    @SerializedName("last_read")
    @Expose
    private String lastRead;

    @SerializedName("latest")
    @Expose
    private Latest latest;

    @SerializedName("unread_count")
    @Expose
    private Integer unread;

    @SerializedName("unread_count_display")
    @Expose
    private Integer unreadDisplay;

    @SerializedName("members")
    @Expose
    private List<String> members;

    @SerializedName("topic")
    @Expose
    private ValueHolder topic;

    @SerializedName("purpose")
    @Expose
    private ValueHolder purpose;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChannel() {
        return channel;
    }

    public void setChannel(boolean channel) {
        this.channel = channel;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isGeneral() {
        return general;
    }

    public void setGeneral(boolean general) {
        this.general = general;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public String getLastRead() {
        return lastRead;
    }

    public void setLastRead(String lastRead) {
        this.lastRead = lastRead;
    }

    public Latest getLatest() {
        return latest;
    }

    public void setLatest(Latest latest) {
        this.latest = latest;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    public Integer getUnreadDisplay() {
        return unreadDisplay;
    }

    public void setUnreadDisplay(Integer unreadDisplay) {
        this.unreadDisplay = unreadDisplay;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public ValueHolder getTopic() {
        return topic;
    }

    public void setTopic(ValueHolder topic) {
        this.topic = topic;
    }

    public ValueHolder getPurpose() {
        return purpose;
    }

    public void setPurpose(ValueHolder purpose) {
        this.purpose = purpose;
    }

    public class Latest {

        @SerializedName("user")
        @Expose
        private String user;

        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("subtype")
        @Expose
        private String subType;

        @SerializedName("text")
        @Expose
        private String text;

        @SerializedName("ts")
        @Expose
        private String timestamp;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSubType() {
            return subType;
        }

        public void setSubType(String subType) {
            this.subType = subType;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

    public class ValueHolder {

        @SerializedName("value")
        @Expose
        private String value;

        @SerializedName("creator")
        @Expose
        private String creator;

        @SerializedName("last_set")
        @Expose
        private Integer lastSet;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public Integer getLastSet() {
            return lastSet;
        }

        public void setLastSet(Integer lastSet) {
            this.lastSet = lastSet;
        }
    }

}