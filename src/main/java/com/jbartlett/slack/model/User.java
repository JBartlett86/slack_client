package com.jbartlett.slack.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by johnbartlett on 18/08/15.
 */
public class User {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("deleted")
    @Expose
    private boolean deleted;

    @SerializedName("color")
    @Expose
    private String colour;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("tz")
    @Expose
    private String timezone;

    @SerializedName("tz_label")
    @Expose
    private String timezoneLabel;

    @SerializedName("tz_offset")
    @Expose
    private Long timezoneOffset;

    @SerializedName("profile")
    @Expose
    private Profile profile;

    @SerializedName("is_admin")
    @Expose
    private boolean admin;

    @SerializedName("is_owner")
    @Expose
    private boolean owner;

    @SerializedName("is_primary_owner")
    @Expose
    private boolean primaryOwner;

    @SerializedName("is_restricted")
    @Expose
    private boolean restricted;

    @SerializedName("is_ultra_restricted")
    @Expose
    private boolean ultraRestricted;

    @SerializedName("is_bot")
    @Expose
    private boolean bot;

    @SerializedName("has_files")
    @Expose
    private boolean hasFiles;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getColour() {
        return colour;
    }

    public Long getTimezoneOffset() {
        return timezoneOffset;
    }

    public String getTimezoneLabel() {
        return timezoneLabel;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getStatus() {
        return status;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isOwner() {
        return owner;
    }

    public boolean isPrimaryOwner() {
        return primaryOwner;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public boolean isUltraRestricted() {
        return ultraRestricted;
    }

    public boolean isBot() {
        return bot;
    }

    public boolean isHasFiles() {
        return hasFiles;
    }

    public String getFirstName() {
        return profile.getFirstName();
    }

    public String getLastName() {
        return profile.getLastName();
    }

    public String getRealName() {
        return profile.getRealName();
    }

    public String getEmail() {
        return profile.getEmail();
    }

    public String getSkype() {
        return profile.getSkype();
    }

    public String getPhone() {
        return profile.getPhone();
    }

    private class Profile {

        @SerializedName("first_name")
        @Expose
        private String firstName;

        @SerializedName("last_name")
        @Expose
        private String lastName;

        @SerializedName("real_name")
        @Expose
        private String realName;

        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("skype")
        @Expose
        private String skype;

        @SerializedName("phone")
        @Expose
        private String phone;

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getRealName() {
            return realName;
        }

        public String getEmail() {
            return email;
        }

        public String getSkype() {
            return skype;
        }

        public String getPhone() {
            return phone;
        }

    }
}
