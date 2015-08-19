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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public boolean isPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(boolean primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public boolean isUltraRestricted() {
        return ultraRestricted;
    }

    public void setUltraRestricted(boolean ultraRestricted) {
        this.ultraRestricted = ultraRestricted;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSkype() {
            return skype;
        }

        public void setSkype(String skype) {
            this.skype = skype;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }


    }
}
