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

    public String getName() {
        return name;
    }

    public boolean isChannel() {
        return channel;
    }

    public Long getCreated() {
        return created;
    }

    public String getCreator() {
        return creator;
    }

    public boolean isArchived() {
        return archived;
    }

    public boolean isGeneral() {
        return general;
    }

    public boolean isMember() {
        return member;
    }

    public String getLastRead() {
        return lastRead;
    }

    public Latest getLatest() {
        return latest;
    }

    public Integer getUnread() {
        return unread;
    }

    public Integer getUnreadDisplay() {
        return unreadDisplay;
    }

    public List<String> getMembers() {
        return members;
    }

    public ValueHolder getTopic() {
        return topic;
    }

    public ValueHolder getPurpose() {
        return purpose;
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

        public String getType() {
            return type;
        }

        public String getSubType() {
            return subType;
        }

        public String getText() {
            return text;
        }

        public String getTimestamp() {
            return timestamp;
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

        public String getCreator() {
            return creator;
        }

        public Integer getLastSet() {
            return lastSet;
        }

    }

}