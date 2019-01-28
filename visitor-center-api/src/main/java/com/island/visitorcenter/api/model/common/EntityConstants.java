package com.island.visitorcenter.api.model.common;

public interface EntityConstants {
    enum InventoryStatus {
        NEW("Just Added"),
        AVAILABLE("Available for booking"),
        RESERVED("Reserved"),
        CANCELLED("Cancelled");

        private String description;

        InventoryStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }


    enum UserStatus {
        ACTIVE("Active"),
        IN_ACTIVE("Account inactivated"),
        BLOCKED("User Blocked");


        private String description;

        UserStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }


    enum CampsiteStatus {
        ACTIVE("Active"),
        IN_ACTIVE("Campsite not available");

        private String description;

        CampsiteStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }



    enum ReservationStatus {
        RESERVED("Reserved for a user"),
        CANCELLED("Cancelled by User");

        private String description;

        ReservationStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }

}
